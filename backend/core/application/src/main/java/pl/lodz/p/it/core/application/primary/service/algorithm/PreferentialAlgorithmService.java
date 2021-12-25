package pl.lodz.p.it.core.application.primary.service.algorithm;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;
import pl.lodz.p.it.core.shared.exception.BookingException;

/**
 * Class responsible for the main logic of preferential algorithm.
 */
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class PreferentialAlgorithmService {

    final BookingRepositoryPort bookingRepositoryPort;

    final AccountRepositoryPort accountRepositoryPort;

    /**
     * Method responsible for applying the preference to the users that signed up for the activity
     * including newly added booking.
     *
     * @param activity Object of type {@link Activity} representing an activity that is being
     *                 considered
     * @param booking  Object of type {@link Booking} representing a newly added booking
     */
    public void applyPreference(Activity activity, Booking booking) {
        List<Account> usersOrderedByPreference = sortUsersByLoyaltyFactor(activity, booking);

        usersOrderedByPreference
            .subList(0, activity.getCapacity())
            .forEach(account -> applyPreference(account, activity, true, booking));

        usersOrderedByPreference
            .subList(activity.getCapacity(), usersOrderedByPreference.size())
            .forEach(account -> {
                applyPreference(account, activity, false, booking);
                sendNotification(account, activity);
            });
    }

    //Method responsible for sorting users by their loyalty factor
    private List<Account> sortUsersByLoyaltyFactor(Activity activity, Booking booking) {
        return getAllUsersSignedUpForActivity(activity, booking)
            .stream()
            .sorted(Comparator.comparing(Account::getLoyaltyFactor).reversed())
            .collect(Collectors.toList());
    }

    // Method retrieving all users signed up for the provided activity (including newly added booking).
    private List<Account> getAllUsersSignedUpForActivity(Activity activity, Booking booking) {
        List<Booking> allBookings = bookingRepositoryPort.findAll();
        allBookings.add(booking);

        return allBookings.stream()
            .filter(b -> b.getActivity().equals(activity.getNumber()))
            .filter(Booking::getActive)
            .filter(b -> !b.getCompleted())
            .map(Booking::getAccount)
            .map(accountRepositoryPort::find)
            .collect(Collectors.toList());
    }

    //Method responsible for sending a notification when one user was removed from activity due to their insufficient loyalty factor.
    //TODO implement here sending an email to the substituted user
    private void sendNotification(Account account, Activity activity) {
        log.info("Account: {} was removed from activity: {}", account.getLogin(),
            activity.getNumber());
    }

    //Method responsible for setting active and pending fields in the provided booking.
    //It uses account and activity params to set fields of the newly added booking (that is not yet in db)
    private void applyPreference(Account account, Activity activity, boolean preferred,
        Booking booking) {
        Booking consideredBooking;
        if (booking.getAccount().equals(account.getLogin()) &&
            booking.getActivity().equals(activity.getNumber())) {
            consideredBooking = booking;
            consideredBooking.setActive(preferred);
            booking.setPending(!preferred);
        } else {
            consideredBooking = bookingRepositoryPort
                .findByClientAndActivity(account.getLogin(), activity.getNumber()).orElseThrow(
                    BookingException::bookingNotFoundException);
            consideredBooking.setActive(preferred);
            consideredBooking.setPending(!preferred);
            bookingRepositoryPort.update(consideredBooking.getNumber(), consideredBooking);
        }
    }

}

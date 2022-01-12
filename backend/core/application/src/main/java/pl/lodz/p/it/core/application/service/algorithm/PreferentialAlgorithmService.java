package pl.lodz.p.it.core.application.service.algorithm;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(propagation = REQUIRED, isolation = READ_COMMITTED)
public class PreferentialAlgorithmService {

    final BookingRepositoryPort bookingRepositoryPort;

    final AccountRepositoryPort accountRepositoryPort;

    /**
     * Method responsible for applying the preference to the users that signed up for the activity
     * including newly added booking.
     *
     * @param activity   Object of type {@link Activity} representing an activity that is being
     *                   considered
     * @param booking    Object of type {@link Booking} representing a newly added booking
     * @param newlyAdded Flag indicating whether method is used while adding a new booking or
     *                   deactivating already existing one
     */
    public void applyPreference(Activity activity, Booking booking, boolean newlyAdded) {
        List<Account> usersOrderedByPreference = sortUsersByLoyaltyFactor(activity, booking,
            newlyAdded);

        usersOrderedByPreference
            .subList(0,
                activity.getCapacity() < usersOrderedByPreference.size() ? activity.getCapacity()
                    : usersOrderedByPreference.size())
            .forEach(account -> applyPreference(account, activity, true, booking));

        if (activity.getCapacity() < usersOrderedByPreference.size()) {
            usersOrderedByPreference
                .subList(activity.getCapacity(), usersOrderedByPreference.size())
                .forEach(account -> {
                    applyPreference(account, activity, false, booking);
                    sendNotification(account, activity);
                });
        }
    }

    //Method responsible for sorting users by their loyalty factor
    //including or excluding newly added/intended for deactivating booking
    private List<Account> sortUsersByLoyaltyFactor(Activity activity, Booking booking,
        boolean newlyAdded) {
        return getAllUsersSignedUpForActivity(activity, booking, newlyAdded)
            .stream()
            .sorted(Comparator.comparing(Account::getLoyaltyFactor).reversed())
            .collect(Collectors.toList());
    }

    // Method retrieving all users signed up for the provided activity.
    //If newlyAdded flag is true => add newly added booking to considered bookings
    //Else => remove booking that is meant to be cancelled from considered bookings due to its inactivity
    //flag that will be later set in repositories
    private List<Account> getAllUsersSignedUpForActivity(Activity activity, Booking booking,
        boolean newlyAdded) {
        List<Booking> allBookingsByActivity = bookingRepositoryPort
            .findByActivity(activity.getNumber());
        List<Booking> activeAndIncompleteBookings = bookingRepositoryPort
            .findAllByActiveTrueAndCompletedFalse();
        allBookingsByActivity.retainAll(activeAndIncompleteBookings);
        if (newlyAdded) {
            allBookingsByActivity.add(booking);
        } else {
            allBookingsByActivity.remove(booking);
        }

        return allBookingsByActivity.stream()
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

    //Method responsible for setting pending field in the provided booking.
    //It uses account and activity params to set fields of the newly added booking (that is not yet in db)
    private void applyPreference(Account account, Activity activity, boolean preferred,
        Booking booking) {
        Booking consideredBooking;
        //If the booking belongs to the user that invoked the method
        //Update only then, because the booking that belongs to the user invoking method
        //is updated in BookingService
        if (booking.getAccount().equals(account.getLogin()) &&
            booking.getActivity().equals(activity.getNumber())) {
            consideredBooking = booking;
            consideredBooking.setPending(!preferred);
        } else {
            consideredBooking = bookingRepositoryPort
                .findByClientAndActivity(account.getLogin(), activity.getNumber()).orElseThrow(
                    BookingException::bookingNotFoundException);
            consideredBooking.setPending(!preferred);
            bookingRepositoryPort.update(consideredBooking.getNumber(), consideredBooking);
        }
    }

}

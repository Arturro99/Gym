package pl.lodz.p.it.core.application.primary.service.util;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

/**
 * Class responsible for the logic of preferential algorithm.
 */
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PreferentialAlgorithm {

    BookingRepositoryPort bookingRepositoryPort;

    /**
     * Method responsible for checking whether activity is already full.
     *
     * @param activity Object of type {@link Activity}
     * @return True if activity is full, false otherwise.
     */
    public boolean isActivityFull(Activity activity) {
        List<Booking> allBookings = bookingRepositoryPort.findAll();
        int capacity = activity.getCapacity();
        long bookingsNumber = allBookings.stream()
            .filter(Booking::getActive)
            .filter(b -> !b.getCompleted())
            .map(Booking::getActivity)
            .map(Activity::getNumber)
            .filter(number -> number.equals(activity.getNumber()))
            .count();

        return bookingsNumber >= capacity;
    }

    /**
     * Method responsible for applying the preference to the users that signed up for the activity.
     *
     * @param activity Object of type {@link Activity} representing an activity that is being
     *                 considered
     */
    public void applyPreference(Activity activity) {
        List<Account> usersOrderedByPreference = sortUsersByLoyaltyFactor(activity);

        usersOrderedByPreference
            .subList(0, activity.getCapacity())
            .forEach(account -> applyPreference(account, activity, true));

        usersOrderedByPreference
            .subList(activity.getCapacity(), usersOrderedByPreference.size())
            .forEach(account -> {
                applyPreference(account, activity, false);
                sendNotification(account, activity);
            });
    }

    //Method responsible for sorting users by their loyalty factor
    private List<Account> sortUsersByLoyaltyFactor(Activity activity) {
        return getAllUsersSignedUpForActivity(activity)
            .stream()
            .sorted(Comparator.comparing(Account::getLoyaltyFactor).reversed())
            .collect(Collectors.toList());
    }

    // Method retrieving all users signed up for the provided activity.
    private List<Account> getAllUsersSignedUpForActivity(Activity activity) {
        List<Booking> allBookings = bookingRepositoryPort.findAll();

        return allBookings.stream()
            .filter(booking -> booking.getActivity().getNumber().equals(activity.getNumber()))
            .filter(Booking::getActive)
            .filter(booking -> !booking.getCompleted())
            .map(Booking::getAccount)
            .collect(Collectors.toList());
    }

    //Method responsible for sending a notification when one user was removed from activity due to their insufficient loyalty factor.
    //TODO implement here sending an email to the substituted user
    private void sendNotification(Account account, Activity activity) {
        log.info("Account: {} was removed from activity: {}", account.getLogin(),
            activity.getNumber());
    }

    //Method responsible for setting active and pending fields in the provided booking.
    private void applyPreference(Account account, Activity activity, boolean preferred) {
        Booking booking = bookingRepositoryPort
            .findByClientAndActivity(account.getLogin(), activity.getNumber());
        booking.setActive(preferred);
        booking.setPending(!preferred);
        bookingRepositoryPort.save(booking);
    }

}

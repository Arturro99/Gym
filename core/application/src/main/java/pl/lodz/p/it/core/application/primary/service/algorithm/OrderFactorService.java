package pl.lodz.p.it.core.application.primary.service.algorithm;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.Comparator;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

/**
 * Service responsible for handling order-side algorithm's factor - the order of bookings for an
 * activity (considering time of submitting).
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
@Service
public class OrderFactorService {

    final BookingRepositoryPort bookingRepositoryPort;

    final AccountRepositoryPort accountRepositoryPort;

    @Value("${algorithm.activity.first-on-list}")
    float firstOneToBookFactor;

    @Value("${algorithm.activity.difference-between-users-on-list}")
    float differenceBetweenNextOnesToBookFactor;

    @Autowired
    public OrderFactorService(BookingRepositoryPort bookingRepositoryPort,
        AccountRepositoryPort accountRepositoryPort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.accountRepositoryPort = accountRepositoryPort;
    }


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
     * Method responsible for calculating and updating user's loyalty factor. The result of
     * calculation is determined by the order of bookings considering time of their submitting.
     *
     * @param activity Activity that is considered
     */
    public void calculateBookingOrderFactor(Activity activity, Account account) {
        float loyaltyFactor = account.getLoyaltyFactor();
        long positionOnTheList = getPositionOnTheList(activity, account);
        if (positionOnTheList <= 5) {
            account.setLoyaltyFactor(
                positionOnTheList == 0 ?
                    loyaltyFactor * firstOneToBookFactor :
                    loyaltyFactor * (firstOneToBookFactor
                        - differenceBetweenNextOnesToBookFactor * (positionOnTheList)));

            accountRepositoryPort.save(account);
        }
    }

    //Method responsible for retrieving the position on the list of bookings for the particular
    //activity. Position means here a place in an ordered queue when the first spot is for user who
    //was the first one to book a place.
    private long getPositionOnTheList(Activity activity, Account account) {
        List<Booking> list = bookingRepositoryPort.findByActivity(activity.getNumber());
        list.sort(Comparator.comparing(Booking::getCreationDate));
        Booking userBooking = bookingRepositoryPort
            .findByClientAndActivity(account.getLogin(), activity.getNumber());
        return list.indexOf(userBooking);
    }
}

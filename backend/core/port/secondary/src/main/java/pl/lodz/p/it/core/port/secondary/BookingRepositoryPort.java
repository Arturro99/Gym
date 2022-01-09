package pl.lodz.p.it.core.port.secondary;

import java.util.List;
import java.util.Optional;
import pl.lodz.p.it.core.domain.Booking;

/**
 * Interface responsible for integrating booking repository with services.
 */
public interface BookingRepositoryPort extends BaseRepositoryPort<Booking> {

    List<Booking> findByClient(String login);

    List<Booking> findByActivity(String number);

    Optional<Booking> findByClientAndActivity(String login, String number);

    Booking findByClientAndNumber(String login, String number);

    List<Booking> findAllByActiveTrueAndCompletedFalse();

    List<Booking> findAllByActiveTrueAndCompletedTrue();

    /**
     * Method responsible for cancelling a particular booking.
     *
     * @param number Booking's number
     * @return Updated booking
     */
    Booking cancelBooking(String number);

    /**
     * Method responsible for completing a particular booking due to activity termination.
     *
     * @param number Booking's number
     * @return Updated booking
     */
    Booking completeBooking(String number);
}

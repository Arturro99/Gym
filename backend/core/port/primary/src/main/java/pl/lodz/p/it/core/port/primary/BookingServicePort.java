package pl.lodz.p.it.core.port.primary;

import pl.lodz.p.it.core.domain.Booking;

import java.util.List;

/**
 * Interface responsible for integrating booking controller with services.
 */
public interface BookingServicePort extends BaseServicePort<Booking> {

    List<Booking> findByClient(String login);

    List<Booking> findByActivity(String number);

    Booking findByClientAndActivity(String login, String number);

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

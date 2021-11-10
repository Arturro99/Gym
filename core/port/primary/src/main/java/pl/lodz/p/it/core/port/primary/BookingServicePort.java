package pl.lodz.p.it.core.port.primary;

import pl.lodz.p.it.core.domain.Booking;

import java.util.List;

/**
 * Interface responsible for integrating booking controller with services.
 */
public interface BookingServicePort extends BaseServicePort<Booking> {

    List<Booking> findByClient(String login);

    List<Booking> findByActivity(String number);
}

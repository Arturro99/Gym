package pl.lodz.p.it.core.port.secondary;

import pl.lodz.p.it.core.domain.Booking;

import java.util.List;

/**
 * Interface responsible for integrating booking repository with services.
 */
public interface BookingRepositoryPort extends BaseRepositoryPort<Booking> {

    List<Booking> findByClient(String login);

    List<Booking> findByActivity(String number);
}

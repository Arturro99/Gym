package pl.lodz.p.it.core.port.secondary;

import java.util.Optional;
import pl.lodz.p.it.core.domain.Booking;

import java.util.List;

/**
 * Interface responsible for integrating booking repository with services.
 */
public interface BookingRepositoryPort extends BaseRepositoryPort<Booking> {

    List<Booking> findByClient(String login);

    List<Booking> findByActivity(String number);

    Optional<Booking> findByClientAndActivity(String login, String number);

    List<Booking> findAllByActiveTrueAndCompletedFalse();

    List<Booking> findAllByActiveTrueAndCompletedTrue();
}

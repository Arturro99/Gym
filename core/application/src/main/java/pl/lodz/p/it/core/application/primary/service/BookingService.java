package pl.lodz.p.it.core.application.primary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.primary.BookingServicePort;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

/**
 * Service class responsible for operating on booking objects.
 */
@Component
public class BookingService extends BaseService<Booking> implements
        BookingServicePort {

    @Autowired
    public BookingService(BookingRepositoryPort bookingRepositoryPort) {
        super(bookingRepositoryPort);
    }

}

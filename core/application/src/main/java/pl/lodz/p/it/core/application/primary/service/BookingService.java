package pl.lodz.p.it.core.application.primary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.primary.BookingServicePort;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

import java.util.List;

/**
 * Service class responsible for operating on booking objects.
 */
@Service
public class BookingService extends BaseService<Booking> implements
        BookingServicePort {

    private final BookingRepositoryPort bookingRepositoryPort;

    @Autowired
    public BookingService(BookingRepositoryPort bookingRepositoryPort) {
        super(bookingRepositoryPort);
        this.bookingRepositoryPort = bookingRepositoryPort;
    }

    @Override
    public List<Booking> findByClient(String login) {
        return bookingRepositoryPort.findByClient(login);
    }

    @Override
    public List<Booking> findByActivity(String number) {
        return bookingRepositoryPort.findByActivity(number);
    }
}

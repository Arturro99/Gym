package pl.lodz.p.it.core.application.primary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.primary.BookingServicePort;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

/**
 * Service class responsible for operating on booking objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService extends BaseService<Booking> implements
    BookingServicePort {

    BookingRepositoryPort bookingRepositoryPort;

    ActivityRepositoryPort activityRepositoryPort;

    @Autowired
    public BookingService(BookingRepositoryPort bookingRepositoryPort,
        ActivityRepositoryPort activityRepositoryPort) {
        super(bookingRepositoryPort);
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.activityRepositoryPort = activityRepositoryPort;
    }

    @Override
    public List<Booking> findByClient(String login) {
        return bookingRepositoryPort.findByClient(login);
    }

    @Override
    public List<Booking> findByActivity(String number) {
        return bookingRepositoryPort.findByActivity(number);
    }

    @Override
    public Booking save(Booking booking) {

        return repository.save(booking);
    }

//    private boolean canParticipateInActivity(Activity activity, Account account,
//        List<Booking> allBookings) {
//
//    }
}

package pl.lodz.p.it.core.application.primary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.application.primary.service.algorithm.OrderFactorService;
import pl.lodz.p.it.core.application.primary.service.algorithm.PreferentialAlgorithmService;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.primary.BookingServicePort;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;
import pl.lodz.p.it.core.shared.exception.BookingAvoidableException;
import pl.lodz.p.it.core.shared.exception.BookingException;

/**
 * Service class responsible for operating on booking objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingService extends BaseService<Booking> implements
    BookingServicePort {

    final BookingRepositoryPort bookingRepositoryPort;

    final AccountRepositoryPort accountRepositoryPort;

    final OrderFactorService orderFactorService;

    final PreferentialAlgorithmService algorithm;

    final ActivityRepositoryPort activityRepositoryPort;

    @Autowired
    public BookingService(BookingRepositoryPort bookingRepositoryPort,
        AccountRepositoryPort accountRepositoryPort,
        OrderFactorService orderFactorService,
        PreferentialAlgorithmService algorithm,
        ActivityRepositoryPort activityRepositoryPort) {
        super(bookingRepositoryPort);
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.accountRepositoryPort = accountRepositoryPort;
        this.orderFactorService = orderFactorService;
        this.algorithm = algorithm;
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
    public Booking findByClientAndActivity(String login, String number) {
        return bookingRepositoryPort.findByClientAndActivity(login, number)
            .orElseThrow(BookingException::bookingNotFoundException);
    }

    @Override
    public Booking save(Booking booking) {
        Activity activity = activityRepositoryPort.find(booking.getActivity());
        Account account = accountRepositoryPort.find(booking.getAccount());
        Optional<Booking> existingBooking;

        try {
            existingBooking = bookingRepositoryPort
                .findByClientAndActivity(account.getLogin(), activity.getNumber());
        } catch (BookingAvoidableException ex) {
            booking.setActivity(activity.getNumber());
            booking.setAccount(account.getLogin());
            booking.setActive(true);
            booking.setCompleted(false);
            booking.setPending(false);
            booking.setCreationDate(OffsetDateTime.now());
            orderFactorService.calculateBookingOrderFactor(booking);
            if (orderFactorService.isActivityFull(activity, booking)) {
                algorithm.applyPreference(activity, booking);
            }
            return repository.save(booking);
        }
        if (!existingBooking.get().getActive()) {
            existingBooking.get().setActive(true);
            if (orderFactorService.isActivityFull(activity)) {
                algorithm.applyPreference(activity, existingBooking.get());
            }
        } else {
            throw BookingException.bookingConflictException();
        }
        return repository.update(existingBooking.get().getNumber(), existingBooking.get());
    }
}

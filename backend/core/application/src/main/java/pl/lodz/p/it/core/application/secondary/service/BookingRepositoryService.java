package pl.lodz.p.it.core.application.secondary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.application.secondary.mapper.BookingMapper;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;
import pl.lodz.p.it.core.shared.constant.Level;
import pl.lodz.p.it.core.shared.exception.AccessLevelException;
import pl.lodz.p.it.core.shared.exception.AccountException;
import pl.lodz.p.it.core.shared.exception.ActivityException;
import pl.lodz.p.it.core.shared.exception.BookingAvoidableException;
import pl.lodz.p.it.core.shared.exception.BookingException;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;
import pl.lodz.p.it.repositoryhibernate.entity.BookingEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;
import pl.lodz.p.it.repositoryhibernate.repository.ActivityRepository;
import pl.lodz.p.it.repositoryhibernate.repository.BookingRepository;

/**
 * Service class responsible for operating on booking repository.
 */
@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED, timeout = 3,
    noRollbackFor = BookingAvoidableException.class)
@Retryable(exclude = {BookingException.class, AccountException.class},
    maxAttemptsExpression = "${retry.maxAttempts}",
    backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
public class BookingRepositoryService extends
    BaseRepositoryService<BookingEntity, Booking> implements
    BookingRepositoryPort {

    private final AccountRepository accountRepository;

    private final BookingRepository bookingRepository;

    private final AccessLevelRepository accessLevelRepository;

    private final ActivityRepository activityRepository;

    @Autowired
    public BookingRepositoryService(BookingRepository repository, BookingMapper mapper,
        AccountRepository accountRepository, AccessLevelRepository accessLevelRepository,
        ActivityRepository activityRepository) {
        super(repository, mapper);
        this.bookingRepository = repository;
        this.accountRepository = accountRepository;
        this.accessLevelRepository = accessLevelRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Booking> findByClient(String login) {
        AccountEntity client = accountRepository.findByBusinessId(login)
            .orElseThrow(AccountException::accountNotFoundException);
        return bookingRepository.findAllByAccount(client).stream()
            .map(mapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByActivity(String number) {
        ActivityEntity activity = activityRepository.findByBusinessId(number)
            .orElseThrow(ActivityException::activityNotFoundException);
        return bookingRepository.findAllByActivity(activity).stream()
            .map(mapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Booking> findByClientAndActivity(String login, String number) {
        AccountEntity client = accountRepository.findByBusinessId(login)
            .orElseThrow(AccountException::accountNotFoundException);
        ActivityEntity activity = activityRepository.findByBusinessId(number)
            .orElseThrow(ActivityException::activityNotFoundException);
        return Optional
            .of(mapper.toDomainModel(bookingRepository.findByAccountAndActivity(client, activity)
                .orElseThrow(BookingAvoidableException::bookingNotFoundException)));
    }

    @Override
    public Booking findByClientAndNumber(String login, String number) {
        AccountEntity client = accountRepository.findByBusinessId(login)
            .orElseThrow(AccountException::accountNotFoundException);
        return mapper.toDomainModel(bookingRepository.findByBusinessIdAndAccount(number, client)
            .orElseThrow(BookingException::bookingNotFoundException));
    }

    @Override
    public List<Booking> findAllByActiveTrueAndCompletedFalse() {
        return bookingRepository.findAllByActiveTrueAndCompletedFalse().stream()
            .map(mapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findAllByActiveTrueAndCompletedTrue() {
        return bookingRepository.findAllByActiveTrueAndCompletedTrue().stream()
            .map(mapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public Booking find(String key) {
        return repository.findByBusinessId(key)
            .map(mapper::toDomainModel)
            .orElseThrow(BookingException::bookingNotFoundException);
    }

    @Override
    public Booking save(Booking booking) {
        AccountEntity account = accountRepository.findByBusinessId(
            booking.getAccount())
            .orElseThrow(AccountException::accountNotFoundException);
        if (!hasClientRole(account)) {
            throw AccessLevelException.illegalAccessLevel();
        }

        ActivityEntity activity = activityRepository
            .findByBusinessId(booking.getActivity())
            .orElseThrow(ActivityException::activityNotFoundException);
        if (isActivityCompleted(activity)) {
            throw ActivityException.activityExpiredException();
        }

        BookingEntity entity = repository.instantiate();
        entity = mapper.toEntityModel(entity, booking);
        entity.setBusinessId(generateNumber());
        entity.setAccount(account);
        entity.setActivity(activity);

        BookingEntity savedEntity = repository.save(entity);
        return mapper.toDomainModel(savedEntity);
    }

    @Override
    public Booking update(String key, Booking booking) {
        BookingEntity entity = repository.findByBusinessId(key).orElseThrow(
            BookingException::bookingNotFoundException);
        BookingEntity updated = mapper
            .toEntityModel(entity, booking);
        BookingEntity response = repository.save(updated);
        return mapper.toDomainModel(response);
    }

    @Override
    public Booking cancelBooking(String number) {
        BookingEntity entity = repository.findByBusinessId(number).orElseThrow(
            BookingException::bookingNotFoundException);
        Booking booking = Booking.builder()
            .active(false)
            .build();

        BookingEntity updated = mapper
            .toEntityModel(entity, booking);
        BookingEntity response = repository.save(updated);

        return mapper.toDomainModel(response);
    }

    @Override
    public Booking completeBooking(String number) {
        BookingEntity entity = repository.findByBusinessId(number).orElseThrow(
            BookingException::bookingNotFoundException);
        Booking booking = Booking.builder()
            .completed(true)
            .active(false)
            .build();

        BookingEntity updated = mapper
            .toEntityModel(entity, booking);
        BookingEntity response = repository.save(updated);

        return mapper.toDomainModel(response);
    }

    private boolean hasClientRole(AccountEntity accountEntity) {
        return accessLevelRepository.findByAccount(accountEntity).stream()
            .map(AccessLevelEntity::getBusinessId)
            .anyMatch(x -> x.equals(Level.CLIENT.name()));
    }

    private boolean isActivityCompleted(ActivityEntity activityEntity) {
        return !activityEntity.getActive();
    }

    private String generateNumber() {
        StringBuilder builder = new StringBuilder("BOO");
        long bookingsNumber = bookingRepository.findAll().stream()
            .distinct()
            .count();
        if (bookingsNumber < 9) {
            builder.append("00".concat(String.valueOf(bookingsNumber + 1)));
        } else if (bookingsNumber < 99) {
            builder.append("0".concat(String.valueOf(bookingsNumber + 1)));
        } else {
            builder.append(bookingsNumber);
        }

        return builder.toString();
    }
}

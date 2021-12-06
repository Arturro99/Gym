package pl.lodz.p.it.repositoryhibernate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;
import pl.lodz.p.it.repositoryhibernate.entity.BookingEntity;

/**
 * Repository responsible for managing entities of type {@link BookingEntity}.
 */
@Repository
public interface BookingRepository extends BaseRepository<BookingEntity> {

    @Override
    default BookingEntity instantiate() {
        return new BookingEntity();
    }

    List<BookingEntity> findAllByAccount(AccountEntity accountEntity);

    List<BookingEntity> findAllByActivity(ActivityEntity activityEntity);

    Optional<BookingEntity> findByAccountAndActivity(AccountEntity accountEntity,
        ActivityEntity activityEntity);

    List<BookingEntity> findAllByActiveTrueAndCompletedFalse();

    List<BookingEntity> findAllByActiveTrueAndCompletedTrue();
}

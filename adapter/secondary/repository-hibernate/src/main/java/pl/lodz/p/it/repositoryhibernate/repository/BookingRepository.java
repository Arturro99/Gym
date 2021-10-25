package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
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
}

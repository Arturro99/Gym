package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.BookingEntity;

/**
 * Repository responsible for managing entities of type {@link BookingEntity}.
 */
@Repository
public interface BookingRepository extends BaseRepository<BookingEntity> {

//    /**
//     * Method responsible for finding a object of type {@link BookingEntity} with the provided
//     * number.
//     *
//     * @param number Booking's business identifier
//     * @return Booking with provided number
//     */
//    Optional<BookingEntity> findByNumber(String number);
}

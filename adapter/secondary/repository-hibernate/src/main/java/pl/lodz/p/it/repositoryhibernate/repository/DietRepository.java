package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.DietEntity;

/**
 * Repository responsible for managing entities of type {@link DietEntity}.
 */
@Repository
public interface DietRepository extends BaseRepository<DietEntity> {

//    /**
//     * Method responsible for finding a object of type {@link DietEntity} with the provided
//     * number.
//     *
//     * @param number Diet's business identifier
//     * @return Diet with provided number
//     */
//    Optional<DietEntity> findByNumber(String number);
}

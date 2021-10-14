package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;

/**
 * Repository responsible for managing entities of type {@link ActivityEntity}.
 */
@Repository
public interface ActivityRepository extends BaseRepository<AccessLevelEntity> {

//    /**
//     * Method responsible for finding a object of type {@link ActivityEntity} with the provided
//     * number.
//     *
//     * @param number Activity's business identifier
//     * @return Activity with provided number
//     */
//    Optional<ActivityEntity> findByNumber(String number);
}

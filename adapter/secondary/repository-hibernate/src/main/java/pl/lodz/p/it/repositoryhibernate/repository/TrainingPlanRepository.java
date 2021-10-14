package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;

/**
 * Repository responsible for managing entities of type {@link TrainingPlanEntity}.
 */
@Repository
public interface TrainingPlanRepository extends BaseRepository<TrainingPlanEntity> {

//    /**
//     * Method responsible for finding a object of type {@link TrainingPlanEntity} with the provided
//     * number.
//     *
//     * @param number Training plan's business identifier
//     * @return Training plan with provided number
//     */
//    Optional<TrainingPlanEntity> findByNumber(String number);
}

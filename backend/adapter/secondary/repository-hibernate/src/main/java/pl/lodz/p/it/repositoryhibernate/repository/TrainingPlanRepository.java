package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;

/**
 * Repository responsible for managing entities of type {@link TrainingPlanEntity}.
 */
@Repository
public interface TrainingPlanRepository extends BaseRepository<TrainingPlanEntity> {

    @Override
    default TrainingPlanEntity instantiate() {
        return new TrainingPlanEntity();
    }
}

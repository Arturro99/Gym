package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;

/**
 * Repository responsible for managing entities of type {@link TrainingPlanEntity}.
 */
@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlanEntity, Long> {

}

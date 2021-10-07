package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingTypeEntity;

/**
 * Repository responsible for managing entities of type {@link TrainingTypeEntity}.
 */
@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingTypeEntity, Long> {

}

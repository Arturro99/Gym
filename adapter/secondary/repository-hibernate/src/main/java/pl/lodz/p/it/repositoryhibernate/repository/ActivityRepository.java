package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;

/**
 * Repository responsible for managing entities of type {@link ActivityEntity}.
 */
@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

}

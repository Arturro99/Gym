package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.DietEntity;

/**
 * Repository responsible for managing entities of type {@link DietEntity}.
 */
@Repository
public interface DietRepository extends JpaRepository<DietEntity, Long> {

}

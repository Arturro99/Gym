package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.DietTypeEntity;

/**
 * Repository responsible for managing entities of type {@link DietTypeEntity}.
 */
@Repository
public interface DietTypeRepository extends JpaRepository<DietTypeEntity, Long> {

}

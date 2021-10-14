package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;

/**
 * Repository responsible for managing entities of type {@link AccessLevelEntity}.
 */
@Repository
public interface AccessLevelRepository extends JpaRepository<AccessLevelEntity, Long> {

}
package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.DisposableUrlEntity;

/**
 * Repository responsible for managing entities of type {@link DisposableUrlEntity}.
 */
@Repository
public interface DisposableUrlRepository extends JpaRepository<DisposableUrlEntity, Long> {

}

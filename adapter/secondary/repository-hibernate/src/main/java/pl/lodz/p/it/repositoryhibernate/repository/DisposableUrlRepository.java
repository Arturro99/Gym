package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.DisposableUrlEntity;

/**
 * Repository responsible for managing entities of type {@link DisposableUrlEntity}.
 */
@Repository
public interface DisposableUrlRepository extends BaseRepository<DisposableUrlEntity> {

    @Override
    default DisposableUrlEntity instantiate() {
        return new DisposableUrlEntity();
    }
}

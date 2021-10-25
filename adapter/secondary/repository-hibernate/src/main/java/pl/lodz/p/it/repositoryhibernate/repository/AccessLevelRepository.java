package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;

/**
 * Repository responsible for managing entities of type {@link AccessLevelEntity}.
 */
@Repository
public interface AccessLevelRepository extends BaseRepository<AccessLevelEntity> {

    @Override
    default AccessLevelEntity instantiate() {
        return new AccessLevelEntity();
    }
}

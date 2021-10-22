package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;

/**
 * Repository responsible for managing entities of type {@link ActivityEntity}.
 */
@Repository
public interface ActivityRepository extends BaseRepository<ActivityEntity> {

    @Override
    default ActivityEntity instantiate() {
        return new ActivityEntity();
    }
}

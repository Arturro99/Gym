package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;

import java.util.List;

/**
 * Repository responsible for managing entities of type {@link ActivityEntity}.
 */
@Repository
public interface ActivityRepository extends BaseRepository<ActivityEntity> {

    @Override
    default ActivityEntity instantiate() {
        return new ActivityEntity();
    }

    List<ActivityEntity> findAllByTrainer(AccountEntity accountEntity);
}

package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;

import java.util.List;

/**
 * Repository responsible for managing entities of type {@link AccessLevelEntity}.
 */
@Repository
public interface AccessLevelRepository extends BaseRepository<AccessLevelEntity> {

    @Override
    default AccessLevelEntity instantiate() {
        return new AccessLevelEntity();
    }

    List<AccessLevelEntity> findByAccount(AccountEntity accountEntity);
}

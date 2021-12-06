package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.DietEntity;

/**
 * Repository responsible for managing entities of type {@link DietEntity}.
 */
@Repository
public interface DietRepository extends BaseRepository<DietEntity> {

    @Override
    default DietEntity instantiate() {
        return new DietEntity();
    }
}

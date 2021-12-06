package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.DietTypeEntity;

/**
 * Repository responsible for managing entities of type {@link DietTypeEntity}.
 */
@Repository
public interface DietTypeRepository extends BaseRepository<DietTypeEntity> {

    @Override
    default DietTypeEntity instantiate() {
        return new DietTypeEntity();
    }
}

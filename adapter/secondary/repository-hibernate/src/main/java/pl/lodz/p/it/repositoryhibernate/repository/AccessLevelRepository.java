package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;

/**
 * Repository responsible for managing entities of type {@link AccessLevelEntity}.
 */
@Repository
public interface AccessLevelRepository extends BaseRepository<AccessLevelEntity> {

//    /**
//     * Method responsible for finding a object of type {@link AccessLevelEntity} with the provided
//     * level.
//     *
//     * @param level Name of the level
//     * @return Object with provided level
//     */
//    Optional<AccessLevelEntity> findByLevel(String level);
}

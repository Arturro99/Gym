package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.repositoryhibernate.entity.BaseEntity;

import java.util.Optional;

/**
 * Base repository providing common methods for repositories with concrete entities.
 *
 * @param <T> Type of the particular entity
 */
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    /**
     * Method responsible for finding an entity of type {@link T} with the provided business
     * identifier.
     *
     * @param businessId Object's business identifier
     * @return Object with provided id
     */
    Optional<T> findByBusinessId(String businessId);
}

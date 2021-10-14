package pl.lodz.p.it.repositoryhibernate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Base repository providing common methods for repositories with concrete entities.
 *
 * @param <T> Type of the particular entity
 */
@Repository
public interface BaseRepository<T> extends JpaRepository<T, Long> {

    /**
     * Method responsible for finding an entity of type {@link T} with the provided business
     * identifier.
     *
     * @param businessId Object's business identifier
     * @return Object with provided id
     */
    Optional<T> findByBusinessId(String businessId);
}

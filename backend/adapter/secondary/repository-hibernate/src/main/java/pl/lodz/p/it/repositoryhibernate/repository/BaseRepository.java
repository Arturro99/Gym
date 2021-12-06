package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.repositoryhibernate.entity.BaseEntity;

import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * Base repository providing common methods for repositories with concrete entities.
 *
 * @param <T> Type of the particular entity
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    /**
     * Method responsible for finding an entity of type {@link T} with the provided business
     * identifier.
     *
     * @param businessId Object's business identifier
     * @return Object with provided id
     */
    @Transactional(propagation = REQUIRES_NEW)
    Optional<T> findByBusinessId(String businessId);

    /**
     * Method responsible for creating an instance of an entity not yet being managed by Entity Manager.
     * It's purpose is to instantiate object's fields with default values.
     *
     * @return New object of type {@link T}
     */
    T instantiate();
}

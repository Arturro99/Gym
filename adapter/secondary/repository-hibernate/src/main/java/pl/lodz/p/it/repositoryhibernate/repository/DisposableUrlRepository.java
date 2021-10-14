package pl.lodz.p.it.repositoryhibernate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.DisposableUrlEntity;

/**
 * Repository responsible for managing entities of type {@link DisposableUrlEntity}.
 */
@Repository
public interface DisposableUrlRepository extends BaseRepository<DisposableUrlEntity> {
//
//    /**
//     * Method responsible for finding a object of type {@link DisposableUrlEntity} with the provided
//     * url.
//     *
//     * @param url Disposable url's business identifier
//     * @return Disposable url with provided number
//     */
//    Optional<DisposableUrlEntity> findByUrl(String url);
}

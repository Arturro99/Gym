package pl.lodz.p.it.repositoryhibernate.repository;

import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;

/**
 * Repository responsible for managing entities of type {@link AccountEntity}.
 */
@Repository
public interface AccountRepository extends BaseRepository<AccountEntity> {

//    /**
//     * Method responsible for finding a object of type {@link AccountEntity} with the provided
//     * login.
//     *
//     * @param login User's login
//     * @return Object with provided login
//     */
//    Optional<AccountEntity> findByLogin(String login);
}

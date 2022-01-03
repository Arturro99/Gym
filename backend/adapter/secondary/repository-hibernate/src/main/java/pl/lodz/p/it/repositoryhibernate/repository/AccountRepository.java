package pl.lodz.p.it.repositoryhibernate.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;

/**
 * Repository responsible for managing entities of type {@link AccountEntity}.
 */
@Repository
public interface AccountRepository extends BaseRepository<AccountEntity> {

    @Override
    default AccountEntity instantiate() {
        return new AccountEntity();
    }

    Optional<AccountEntity> findByEmail(String email);
}

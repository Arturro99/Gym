package pl.lodz.p.it.core.application.secondary.mapper;

import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;

/**
 * Interface responsible for mapping {@link Account} objects and {@link AccountEntity}
 */
public interface AccountMapper extends BaseMapper<AccessLevelEntity, AccessLevel> {

}

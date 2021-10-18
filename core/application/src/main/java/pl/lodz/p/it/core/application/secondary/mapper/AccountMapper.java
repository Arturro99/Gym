package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link Account} objects and {@link AccountEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccountMapper extends BaseMapper<AccountEntity, Account> {

}

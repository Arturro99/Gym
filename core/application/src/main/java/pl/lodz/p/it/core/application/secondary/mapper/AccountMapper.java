package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.*;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link Account} objects and {@link AccountEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AccountMapper implements BaseMapper<AccountEntity, Account> {

    @BeforeMapping
    @Override
    public void mapBaseEntityAttributesToDomainModel(AccountEntity entityModel, @MappingTarget Account domainModel) {
        domainModel.setVersion(domainModel.getVersion());
        domainModel.setLogin(entityModel.getBusinessId());
        domainModel.setCreationDate(entityModel.getCreationDate());
        domainModel.setModificationDate(entityModel.getModificationDate());
    }
}

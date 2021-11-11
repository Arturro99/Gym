package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link Account} objects and {@link AccountEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {TrainingPlanMapper.class, DietMapper.class})
public interface AccountMapper extends BaseMapper<AccountEntity, Account> {

    @Override
    @Mapping(source = "businessId", target = "login")
    Account toDomainModel(AccountEntity entityModel);

    @Override
    @Mapping(source = "login", target = "businessId")
    AccountEntity toEntityModel(Account domainModel);

    @Override
    @Mapping(source = "login", target = "businessId")
    AccountEntity toEntityModel(@MappingTarget AccountEntity accountEntity, Account domainModel);
}

package pl.lodz.p.it.core.application.secondary.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;

/**
 * Interface responsible for mapping {@link Account} objects and {@link AccountEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {TrainingPlanMapper.class, DietMapper.class},
    builder = @Builder(disableBuilder = true))
public interface AccountMapper extends BaseMapper<AccountEntity, Account> {

    @Override
    @Mapping(source = "businessId", target = "login")
    @Mapping(source = "modifiedBy.businessId", target = "modifiedBy")
    Account toDomainModel(AccountEntity entityModel);

    @Override
    @Mapping(source = "login", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    AccountEntity toEntityModel(Account domainModel);

    @Override
    @Mapping(source = "login", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    AccountEntity toEntityModel(@MappingTarget AccountEntity accountEntity, Account domainModel);
}

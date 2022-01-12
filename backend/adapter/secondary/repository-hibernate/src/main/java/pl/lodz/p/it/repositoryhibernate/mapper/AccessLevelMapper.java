package pl.lodz.p.it.repositoryhibernate.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;

/**
 * Interface responsible for mapping {@link AccessLevel} objects and {@link AccessLevelEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AccountMapper.class)
public interface AccessLevelMapper extends BaseMapper<AccessLevelEntity, AccessLevel> {

    @Override
    @Mapping(source = "businessId", target = "level")
    @Mapping(source = "modifiedBy.businessId", target = "modifiedBy")
    @Mapping(source = "createdBy.businessId", target = "createdBy")
    @Mapping(source = "account.businessId", target = "account")
    AccessLevel toDomainModel(AccessLevelEntity entityModel);

    @Override
    @Mapping(source = "level", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "account", target = "account.businessId")
    AccessLevelEntity toEntityModel(AccessLevel domainModel);

    @Override
    @Mapping(source = "level", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "account", target = "account.businessId")
    AccessLevelEntity toEntityModel(@MappingTarget AccessLevelEntity accountEntity,
        AccessLevel domainModel);
}

package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link AccessLevel} objects and {@link AccessLevelEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AccountMapper.class)
public interface AccessLevelMapper extends BaseMapper<AccessLevelEntity, AccessLevel> {

    @Override
    @Mapping(source = "businessId", target = "level")
    AccessLevel toDomainModel(AccessLevelEntity entityModel);

    @Override
    @Mapping(source = "level", target = "businessId")
    AccessLevelEntity toEntityModel(AccessLevel domainModel);

    @Override
    @Mapping(source = "level", target = "businessId")
    AccessLevelEntity toEntityModel(@MappingTarget AccessLevelEntity accountEntity, AccessLevel domainModel);
}

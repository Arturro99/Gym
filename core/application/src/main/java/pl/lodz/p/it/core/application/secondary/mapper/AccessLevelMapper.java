package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link AccessLevel} objects and {@link AccessLevelEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AccessLevelMapper implements BaseMapper<AccessLevelEntity, AccessLevel> {


}

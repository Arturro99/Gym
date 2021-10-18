package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link AccessLevel} objects and {@link AccessLevelEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccessLevelMapper extends BaseMapper<AccessLevelEntity, AccessLevel> {


}

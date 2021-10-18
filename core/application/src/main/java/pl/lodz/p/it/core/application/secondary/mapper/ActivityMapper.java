package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link Activity} objects and {@link ActivityEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface ActivityMapper extends BaseMapper<ActivityEntity, Activity> {

}

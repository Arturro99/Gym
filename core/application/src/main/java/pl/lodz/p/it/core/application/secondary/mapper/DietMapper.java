package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.repositoryhibernate.entity.DietEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link Diet} objects and {@link DietEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface DietMapper extends BaseMapper<DietEntity, Diet> {

}

package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.TrainingType;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingTypeEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link TrainingType} objects and {@link TrainingTypeEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TrainingTypeMapper implements BaseMapper<TrainingTypeEntity, TrainingType> {

}

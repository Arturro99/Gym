package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link TrainingPlan} objects and {@link TrainingPlanEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TrainingPlanMapper implements BaseMapper<TrainingPlanEntity, TrainingPlan> {

}

package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link TrainingPlan} objects and {@link TrainingPlanEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AccountMapper.class, TrainingTypeMapper.class})
public interface TrainingPlanMapper extends BaseMapper<TrainingPlanEntity, TrainingPlan> {

    @Override
    @Mapping(source = "businessId", target = "number")
    TrainingPlan toDomainModel(TrainingPlanEntity entityModel);

    @Override
    @Mapping(source = "domainModel.number", target = "businessId")
    TrainingPlanEntity toEntityModel(@MappingTarget TrainingPlanEntity entityModel, TrainingPlan domainModel);
}

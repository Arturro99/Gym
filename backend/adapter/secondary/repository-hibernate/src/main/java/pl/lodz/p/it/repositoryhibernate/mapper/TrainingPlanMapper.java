package pl.lodz.p.it.repositoryhibernate.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;

/**
 * Interface responsible for mapping {@link TrainingPlan} objects and {@link TrainingPlanEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AccountMapper.class,
    TrainingTypeMapper.class})
public interface TrainingPlanMapper extends BaseMapper<TrainingPlanEntity, TrainingPlan> {

    @Override
    @Mapping(source = "businessId", target = "number")
    @Mapping(source = "modifiedBy.businessId", target = "modifiedBy")
    @Mapping(source = "createdBy.businessId", target = "createdBy")
    @Mapping(source = "trainer.businessId", target = "trainer")
    @Mapping(source = "trainingType.businessId", target = "trainingType")
    TrainingPlan toDomainModel(TrainingPlanEntity entityModel);

    @Override
    @Mapping(source = "domainModel.number", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "trainer", target = "trainer.businessId")
    @Mapping(source = "trainingType", target = "trainingType.businessId")
    TrainingPlanEntity toEntityModel(TrainingPlan domainModel);

    @Override
    @Mapping(source = "domainModel.number", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "trainer", target = "trainer.businessId")
    @Mapping(source = "trainingType", target = "trainingType.businessId")
    TrainingPlanEntity toEntityModel(@MappingTarget TrainingPlanEntity entityModel,
        TrainingPlan domainModel);
}

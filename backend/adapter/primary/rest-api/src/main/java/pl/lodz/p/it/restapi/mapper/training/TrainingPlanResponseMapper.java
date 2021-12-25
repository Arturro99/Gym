package pl.lodz.p.it.restapi.mapper.training;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.restapi.dto.TrainingPlanResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrainingPlanResponseMapper extends BaseResponseMapper<TrainingPlanResponse, TrainingPlan> {

    @Override
    @Mapping(source = "name", target = "title")
    TrainingPlanResponse toDtoModel(TrainingPlan domainModel);

    @Override
    @Mapping(source = "name", target = "title")
    TrainingPlanResponse toDtoModel(@MappingTarget TrainingPlanResponse dtoModel, TrainingPlan domainModel);
}

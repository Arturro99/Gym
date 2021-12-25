package pl.lodz.p.it.restapi.mapper.training;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.restapi.dto.TrainingPlanRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrainingPlanRequestPostMapper extends
    BaseRequestMapper<TrainingPlanRequestPost, TrainingPlan> {

    @Override
    @Mapping(source = "title", target = "name")
    @Mapping(source = "trainingPlanNumber", target = "number")
    TrainingPlan toDomainModel(TrainingPlanRequestPost dtoModel);
}

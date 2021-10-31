package pl.lodz.p.it.restapi.mapper.training;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.restapi.dto.TrainingPlanRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrainingPlanRequestPostMapper extends BaseRequestMapper<TrainingPlanRequestPost, TrainingPlan> {

    @Override
    @Mapping(target = "trainer", ignore = true)
    @Mapping(target = "trainingType", ignore = true)
    TrainingPlan toDomainModel(TrainingPlanRequestPost dtoModel);
}

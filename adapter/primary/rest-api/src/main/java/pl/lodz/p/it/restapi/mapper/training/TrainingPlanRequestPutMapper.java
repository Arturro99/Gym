package pl.lodz.p.it.restapi.mapper.training;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.restapi.dto.TrainingPlanRequestPut;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrainingPlanRequestPutMapper extends BaseRequestMapper<TrainingPlanRequestPut, TrainingPlan> {

    @Override
    @Mapping(target = "trainer", ignore = true)
    TrainingPlan toDomainModel(TrainingPlanRequestPut dtoModel);
}

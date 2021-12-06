package pl.lodz.p.it.restapi.mapper.training;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.restapi.dto.TrainingPlanRequestPut;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrainingPlanRequestPutMapper extends
    BaseRequestMapper<TrainingPlanRequestPut, TrainingPlan> {

    @Override
    @Mapping(target = "trainer.login", source = "trainer")
    @Mapping(target = "trainingType.name", source = "trainingType")
    TrainingPlan toDomainModel(TrainingPlanRequestPut dtoModel);
}

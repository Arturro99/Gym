package pl.lodz.p.it.restapi.mapper.training;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.restapi.dto.TrainingPlanResponse1;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrainingPlanResponseMapper extends BaseResponseMapper<TrainingPlanResponse1, TrainingPlan> {

}

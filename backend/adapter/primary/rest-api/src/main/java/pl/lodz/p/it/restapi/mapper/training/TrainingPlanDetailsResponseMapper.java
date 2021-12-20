package pl.lodz.p.it.restapi.mapper.training;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.restapi.dto.TrainingPlanDetailsResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrainingPlanDetailsResponseMapper extends
    BaseResponseMapper<TrainingPlanDetailsResponse, TrainingPlan> {

}

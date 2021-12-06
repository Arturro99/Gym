package pl.lodz.p.it.restapi.mapper.training;

import org.mapstruct.Mapper;
import pl.lodz.p.it.restapi.dto.TrainingType;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrainingTypeResponseMapper extends BaseResponseMapper<TrainingType, pl.lodz.p.it.core.domain.TrainingType> {

}

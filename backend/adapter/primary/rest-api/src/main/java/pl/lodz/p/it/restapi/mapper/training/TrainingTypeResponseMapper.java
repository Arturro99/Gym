package pl.lodz.p.it.restapi.mapper.training;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.lodz.p.it.core.domain.TrainingType;
import pl.lodz.p.it.restapi.dto.TrainingTypeResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrainingTypeResponseMapper extends
    BaseResponseMapper<TrainingTypeResponse, TrainingType> {

    @Override
    @Mapping(source = "name", target = "title")
    TrainingTypeResponse toDtoModel(TrainingType domainModel);

    @Override
    @Mapping(source = "name", target = "title")
    TrainingTypeResponse toDtoModel(@MappingTarget TrainingTypeResponse dtoModel,
        TrainingType domainModel);
}

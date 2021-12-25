package pl.lodz.p.it.restapi.mapper.training;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.lodz.p.it.restapi.dto.TrainingType;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrainingTypeResponseMapper extends BaseResponseMapper<TrainingType, pl.lodz.p.it.core.domain.TrainingType> {

    @Override
    @Mapping(source = "name", target = "title")
    TrainingType toDtoModel(pl.lodz.p.it.core.domain.TrainingType domainModel);

    @Override
    @Mapping(source = "name", target = "title")
    TrainingType toDtoModel(@MappingTarget TrainingType dtoModel,
        pl.lodz.p.it.core.domain.TrainingType domainModel);
}

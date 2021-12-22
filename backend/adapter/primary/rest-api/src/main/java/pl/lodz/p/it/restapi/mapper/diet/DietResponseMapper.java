package pl.lodz.p.it.restapi.mapper.diet;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.restapi.dto.DietResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface DietResponseMapper extends BaseResponseMapper<DietResponse, Diet> {

    @Override
    @Mapping(source = "name", target = "title")
    @Mapping(source = "dietType.name", target = "dietType")
    DietResponse toDtoModel(Diet domainModel);

    @Override
    @Mapping(source = "name", target = "title")
    @Mapping(source = "dietType.name", target = "dietType")
    DietResponse toDtoModel(@MappingTarget DietResponse dtoModel, Diet domainModel);
}

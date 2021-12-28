package pl.lodz.p.it.restapi.mapper.diet;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.restapi.dto.DietDetailsResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface DietDetailsResponseMapper extends BaseResponseMapper<DietDetailsResponse, Diet> {

    @Override
    @Mapping(source = "name", target = "title")
    DietDetailsResponse toDtoModel(Diet domainModel);

    @Override
    @Mapping(source = "name", target = "title")
    DietDetailsResponse toDtoModel(@MappingTarget DietDetailsResponse dtoModel, Diet domainModel);
}

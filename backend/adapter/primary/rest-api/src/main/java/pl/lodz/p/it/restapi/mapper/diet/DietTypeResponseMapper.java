package pl.lodz.p.it.restapi.mapper.diet;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.lodz.p.it.core.domain.DietType;
import pl.lodz.p.it.restapi.dto.DietTypeResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface DietTypeResponseMapper extends
    BaseResponseMapper<DietTypeResponse, DietType> {

    @Override
    @Mapping(source = "name", target = "title")
    DietTypeResponse toDtoModel(DietType domainModel);

    @Override
    @Mapping(source = "name", target = "title")
    DietTypeResponse toDtoModel(@MappingTarget DietTypeResponse dtoModel,
        DietType domainModel);
}

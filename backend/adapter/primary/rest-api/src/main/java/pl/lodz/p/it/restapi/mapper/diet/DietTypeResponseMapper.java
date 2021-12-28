package pl.lodz.p.it.restapi.mapper.diet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.lodz.p.it.restapi.dto.DietType;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface DietTypeResponseMapper extends BaseResponseMapper<DietType, pl.lodz.p.it.core.domain.DietType> {

    @Override
    @Mapping(source = "name", target = "title")
    DietType toDtoModel(pl.lodz.p.it.core.domain.DietType domainModel);

    @Override
    @Mapping(source = "name", target = "title")
    DietType toDtoModel(@MappingTarget DietType dtoModel, pl.lodz.p.it.core.domain.DietType domainModel);
}

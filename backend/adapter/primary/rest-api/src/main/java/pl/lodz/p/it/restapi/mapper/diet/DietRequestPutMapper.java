package pl.lodz.p.it.restapi.mapper.diet;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.restapi.dto.DietRequestPut;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface DietRequestPutMapper extends BaseRequestMapper<DietRequestPut, Diet> {

    @Override
    @Mapping(target = "dietType.name", source = "dietType.value")
    @Mapping(source = "title", target = "name")
    Diet toDomainModel(DietRequestPut dtoModel);
}

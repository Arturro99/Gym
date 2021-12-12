package pl.lodz.p.it.restapi.mapper.diet;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.restapi.dto.DietRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface DietRequestPostMapper extends BaseRequestMapper<DietRequestPost, Diet> {

    @Override
    @Mapping(target = "dietType.name", source = "dietType.value")
    Diet toDomainModel(DietRequestPost dtoModel);
}
package pl.lodz.p.it.restapi.mapper.activity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.restapi.dto.ActivityDetailsResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface ActivityDetailsResponseMapper extends
    BaseResponseMapper<ActivityDetailsResponse, Activity> {

    @Override
    @Mapping(source = "name", target = "title")
    ActivityDetailsResponse toDtoModel(Activity domainModel);

    @Override
    @Mapping(source = "name", target = "title")
    ActivityDetailsResponse toDtoModel(@MappingTarget ActivityDetailsResponse dtoModel, Activity domainModel);
}

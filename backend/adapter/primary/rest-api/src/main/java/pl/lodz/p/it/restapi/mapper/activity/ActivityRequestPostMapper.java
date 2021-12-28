package pl.lodz.p.it.restapi.mapper.activity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.restapi.dto.ActivityRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    builder = @Builder(disableBuilder = true))
public interface ActivityRequestPostMapper extends
    BaseRequestMapper<ActivityRequestPost, Activity> {

    @Override
    @Mapping(source = "title", target = "name")
    @Mapping(source = "activityNumber", target = "number")
    Activity toDomainModel(ActivityRequestPost dtoModel);
}

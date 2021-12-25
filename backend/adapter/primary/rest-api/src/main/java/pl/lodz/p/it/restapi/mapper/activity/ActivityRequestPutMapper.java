package pl.lodz.p.it.restapi.mapper.activity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.restapi.dto.ActivityRequestPut;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    builder = @Builder(disableBuilder = true))
public interface ActivityRequestPutMapper extends BaseRequestMapper<ActivityRequestPut, Activity> {

    @Override
    @Mapping(source = "title", target = "name")
    Activity toDomainModel(ActivityRequestPut dtoModel);
}

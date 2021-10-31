package pl.lodz.p.it.restapi.mapper.activity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.restapi.dto.ActivityRequestPut;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface ActivityRequestPutMapper extends BaseRequestMapper<ActivityRequestPut, Activity> {

    @Override
    @Mapping(target = "trainer", ignore = true)
    Activity toDomainModel(ActivityRequestPut dtoModel);
}

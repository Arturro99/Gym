package pl.lodz.p.it.restapi.mapper.activity;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.restapi.dto.ActivityResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface ActivityResponseMapper extends BaseResponseMapper<ActivityResponse, Activity> {

}

package pl.lodz.p.it.restapi.mapper.accessLevel;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.restapi.dto.AccessLevelResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccessLevelResponseMapper extends BaseResponseMapper<AccessLevelResponse, AccessLevel> {

}

package pl.lodz.p.it.restapi.mapper;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.restapi.dto.AccessLevelResponse;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccessLevelResponseMapper extends BaseMapper<AccessLevelResponse, AccessLevel> {

}

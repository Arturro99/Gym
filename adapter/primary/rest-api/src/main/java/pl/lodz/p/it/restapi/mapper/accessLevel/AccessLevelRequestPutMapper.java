package pl.lodz.p.it.restapi.mapper.accessLevel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.restapi.dto.AccessLevelRequestPut;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccessLevelRequestPutMapper extends BaseRequestMapper<AccessLevelRequestPut, AccessLevel> {

    @Override
    @Mapping(target = "account", ignore = true)
    AccessLevel toDomainModel(AccessLevelRequestPut dtoModel);
}

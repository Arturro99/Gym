package pl.lodz.p.it.restapi.mapper.accessLevel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.restapi.dto.AccessLevelRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccessLevelRequestPostMapper extends BaseRequestMapper<AccessLevelRequestPost, AccessLevel> {

    @Override
    @Mapping(target = "account", ignore = true)
    AccessLevel toDomainModel(AccessLevelRequestPost dtoModel);
}

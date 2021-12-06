package pl.lodz.p.it.restapi.mapper.accessLevel;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.restapi.dto.AccessLevelRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface AccessLevelRequestPostMapper extends
    BaseRequestMapper<AccessLevelRequestPost, AccessLevel> {

    @Override
    @Mapping(target = "account.login", source = "dtoModel.accountLogin")
    AccessLevel toDomainModel(AccessLevelRequestPost dtoModel);
}

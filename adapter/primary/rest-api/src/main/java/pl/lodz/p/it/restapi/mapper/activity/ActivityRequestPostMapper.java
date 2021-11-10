package pl.lodz.p.it.restapi.mapper.activity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.restapi.dto.ActivityRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface ActivityRequestPostMapper extends BaseRequestMapper<ActivityRequestPost, Activity> {

    @Override
    @Mapping(target = "trainer.login", source = "dtoModel.trainer")
    Activity toDomainModel(ActivityRequestPost dtoModel);
}

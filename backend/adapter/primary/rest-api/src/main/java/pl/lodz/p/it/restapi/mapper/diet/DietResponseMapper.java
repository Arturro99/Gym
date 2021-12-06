package pl.lodz.p.it.restapi.mapper.diet;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.restapi.dto.DietResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface DietResponseMapper extends BaseResponseMapper<DietResponse, Diet> {

}

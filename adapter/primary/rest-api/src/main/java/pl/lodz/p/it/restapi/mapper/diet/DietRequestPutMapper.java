package pl.lodz.p.it.restapi.mapper.diet;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.restapi.dto.DietRequestPut;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface DietRequestPutMapper extends BaseRequestMapper<DietRequestPut, Diet> {

}

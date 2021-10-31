package pl.lodz.p.it.restapi.mapper.diet;

import org.mapstruct.Mapper;
import pl.lodz.p.it.restapi.dto.DietType;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface DietTypeResponseMapper extends BaseResponseMapper<DietType, pl.lodz.p.it.core.domain.DietType> {

}

package pl.lodz.p.it.restapi.mapper.booking;

import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.restapi.dto.BookingResponse;
import pl.lodz.p.it.restapi.mapper.BaseResponseMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface BookingResponseMapper extends BaseResponseMapper<BookingResponse, Booking> {

}

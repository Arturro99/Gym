package pl.lodz.p.it.restapi.mapper.booking;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.restapi.dto.BookingRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    builder = @Builder(disableBuilder = true))
public interface BookingRequestPostMapper extends BaseRequestMapper<BookingRequestPost, Booking> {

}

package pl.lodz.p.it.restapi.mapper.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.restapi.dto.BookingRequestPost;
import pl.lodz.p.it.restapi.mapper.BaseRequestMapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface BookingRequestPostMapper extends BaseRequestMapper<BookingRequestPost, Booking> {

    @Override
    @Mapping(target = "account", ignore = true)
    Booking toDomainModel(BookingRequestPost dtoModel);
}

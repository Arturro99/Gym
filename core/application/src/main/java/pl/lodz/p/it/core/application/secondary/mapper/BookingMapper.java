package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.repositoryhibernate.entity.BookingEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link Booking} objects and {@link BookingEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class BookingMapper implements BaseMapper<BookingEntity, Booking> {

}

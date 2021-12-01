package pl.lodz.p.it.core.application.secondary.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.repositoryhibernate.entity.BookingEntity;

/**
 * Interface responsible for mapping {@link Booking} objects and {@link BookingEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AccountMapper.class,
    ActivityMapper.class},
    builder = @Builder(disableBuilder = true))
public interface BookingMapper extends BaseMapper<BookingEntity, Booking> {

    @Override
    @Mapping(source = "businessId", target = "number")
    Booking toDomainModel(BookingEntity entityModel);

    @Override
    @Mapping(source = "number", target = "businessId")
    BookingEntity toEntityModel(Booking domainModel);

    @Override
    @Mapping(source = "number", target = "businessId")
    BookingEntity toEntityModel(@MappingTarget BookingEntity entityModel, Booking domainModel);
}

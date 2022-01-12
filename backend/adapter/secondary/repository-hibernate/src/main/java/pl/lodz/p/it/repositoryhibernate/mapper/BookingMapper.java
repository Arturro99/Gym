package pl.lodz.p.it.repositoryhibernate.mapper;

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
    @Mapping(source = "modifiedBy.businessId", target = "modifiedBy")
    @Mapping(source = "createdBy.businessId", target = "createdBy")
    @Mapping(source = "account.businessId", target = "account")
    @Mapping(source = "activity.businessId", target = "activity")
    Booking toDomainModel(BookingEntity entityModel);

    @Override
    @Mapping(source = "number", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "account", target = "account.businessId")
    @Mapping(source = "activity", target = "activity.businessId")
    BookingEntity toEntityModel(Booking domainModel);

    @Override
    @Mapping(source = "number", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "account", target = "account.businessId")
    @Mapping(source = "activity", target = "activity.businessId")
    BookingEntity toEntityModel(@MappingTarget BookingEntity entityModel, Booking domainModel);
}

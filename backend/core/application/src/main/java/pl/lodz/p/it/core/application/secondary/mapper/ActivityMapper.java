package pl.lodz.p.it.core.application.secondary.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;

/**
 * Interface responsible for mapping {@link Activity} objects and {@link ActivityEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AccountMapper.class,
    builder = @Builder(disableBuilder = true))
public interface ActivityMapper extends BaseMapper<ActivityEntity, Activity> {

    @Override
    @Mapping(source = "businessId", target = "number")
    @Mapping(source = "modifiedBy.businessId", target = "modifiedBy")
    @Mapping(source = "createdBy.businessId", target = "createdBy")
    @Mapping(source = "trainer.businessId", target = "trainer")
    Activity toDomainModel(ActivityEntity entityModel);

    @Override
    @Mapping(source = "number", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "trainer", target = "trainer.businessId")
    ActivityEntity toEntityModel(Activity domainModel);

    @Override
    @Mapping(source = "number", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "trainer", target = "trainer.businessId")
    ActivityEntity toEntityModel(@MappingTarget ActivityEntity entityModel, Activity domainModel);
}

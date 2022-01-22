package pl.lodz.p.it.repositoryhibernate.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.repositoryhibernate.entity.DietEntity;

/**
 * Interface responsible for mapping {@link Diet} objects and {@link DietEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {DietTypeMapper.class, AccountMapper.class})
public interface DietMapper extends BaseMapper<DietEntity, Diet> {

    @Override
    @Mapping(source = "businessId", target = "number")
    @Mapping(source = "modifiedBy.businessId", target = "modifiedBy")
    @Mapping(source = "createdBy.businessId", target = "createdBy")
    @Mapping(source = "dietType.businessId", target = "dietType")
    Diet toDomainModel(DietEntity entityModel);

    @Override
    @Mapping(source = "number", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "dietType", target = "dietType.businessId")
    DietEntity toEntityModel(Diet domainModel);

    @Override
    @Mapping(source = "number", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "dietType", target = "dietType.businessId")
    DietEntity toEntityModel(@MappingTarget DietEntity entityModel, Diet domainModel);
}

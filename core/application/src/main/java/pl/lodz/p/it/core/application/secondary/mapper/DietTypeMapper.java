package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.DietType;
import pl.lodz.p.it.repositoryhibernate.entity.DietTypeEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link DietType} objects and {@link DietTypeEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DietTypeMapper extends BaseMapper<DietTypeEntity, DietType> {

    @Override
    @Mapping(source = "businessId", target = "name")
    DietType toDomainModel(DietTypeEntity entityModel);

    @Override
    @Mapping(source = "name", target = "businessId")
    DietTypeEntity toEntityModel(DietType domainModel);
}

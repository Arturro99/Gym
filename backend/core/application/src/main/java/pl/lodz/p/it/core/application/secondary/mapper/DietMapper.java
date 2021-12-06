package pl.lodz.p.it.core.application.secondary.mapper;

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
    unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DietTypeMapper.class)
public interface DietMapper extends BaseMapper<DietEntity, Diet> {

    @Override
    @Mapping(source = "businessId", target = "number")
    Diet toDomainModel(DietEntity entityModel);

    @Override
    @Mapping(source = "number", target = "businessId")
    DietEntity toEntityModel(Diet domainModel);

    @Override
    @Mapping(source = "number", target = "businessId")
    DietEntity toEntityModel(@MappingTarget DietEntity entityModel, Diet domainModel);
}

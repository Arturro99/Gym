package pl.lodz.p.it.core.application.secondary.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.TrainingType;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingTypeEntity;

/**
 * Interface responsible for mapping {@link TrainingType} objects and {@link TrainingTypeEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TrainingTypeMapper extends BaseMapper<TrainingTypeEntity, TrainingType> {

    @Override
    @Mapping(source = "businessId", target = "name")
    TrainingType toDomainModel(TrainingTypeEntity entityModel);

    @Override
    @Mapping(source = "name", target = "businessId")
    TrainingTypeEntity toEntityModel(TrainingType domainModel);
}

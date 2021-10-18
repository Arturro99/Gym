package pl.lodz.p.it.core.application.secondary.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Interface responsible for delivering base mapping methods
 *
 * @param <T> Type of appropriate entity that is meant to be mapped
 * @param <U> Type of appropriate domain model that is meant to be mapped
 */
public interface BaseMapper<T, U> {

    U toDomainModel(T entityModel);

    @Mapping(target = "businessId", ignore = true)
    T toEntityModel(U domainModel);

    @Mapping(target = "businessId", ignore = true)
    T toEntityModel(@MappingTarget T entityModel, U domainModel);
}

package pl.lodz.p.it.restapi.mapper;

import org.mapstruct.MappingTarget;

/**
 * Interface responsible for delivering base mapping methods between dtos and domain models
 *
 * @param <T> Type of appropriate dto that is meant to be mapped
 * @param <U> Type of appropriate domain model that is meant to be mapped
 */
public interface BaseMapper<T, U> {

    U toDomainModel(T dtoModel);

    T toDtoModel(U domainModel);

    T toDtoModel(@MappingTarget T dtoModel, U domainModel);
}

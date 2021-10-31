package pl.lodz.p.it.restapi.mapper;

import pl.lodz.p.it.core.domain.BaseModel;

/**
 * Interface responsible for delivering base mapping response methods between dtos and domain models.
 *
 * @param <T> Type of appropriate dto that is meant to be mapped
 * @param <U> Type of appropriate domain model extending {@link BaseModel} that is meant to be mapped
 *            that is meant to be mapped
 */
public interface BaseRequestMapper<T, U extends BaseModel> {

    /**
     * Method responsible for mapping object of type {@link T} into an object of type {@link U}.
     *
     * @param dtoModel DTO model object that is meant to be mapped.
     * @return New object created using object's of type {@link T} attributes.
     */
    U toDomainModel(T dtoModel);
}

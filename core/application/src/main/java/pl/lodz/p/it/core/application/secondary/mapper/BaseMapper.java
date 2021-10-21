package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.MappingTarget;
import pl.lodz.p.it.core.domain.BaseModel;
import pl.lodz.p.it.repositoryhibernate.entity.BaseEntity;

/**
 * Interface responsible for delivering base mapping methods
 *
 * @param <T> Type of appropriate entity extending {@link BaseEntity} that is meant to be mapped
 * @param <U> Type of appropriate domain model extending {@link BaseModel} that is meant to be mapped
 */
public interface BaseMapper<T extends BaseEntity, U extends BaseModel> {

    /**
     * Method responsible for mapping attributes that are kept in {@link BaseEntity} class into a new object of
     * {@link U} type.
     *
     * @param entityModel Entity model object that provides attributes used for creating a new object of
     *                    {@link U} type.
     * @param domainModel Domain model object that is a target of the mapping process.
     */
    void mapBaseEntityAttributesToDomainModel(T entityModel, @MappingTarget U domainModel);

    /**
     * Method responsible for mapping object of type {@link T} into an object of type {@link U}.
     *
     * @param entityModel Entity model object that is meant to be mapped.
     * @return New object created using object's of type {@link T} attributes.
     */
    U toDomainModel(T entityModel);

    /**
     * Method responsible for mapping object of type {@link U} into an object of type {@link T}.
     *
     * @param domainModel Domain model object that is meant to be mapped.
     * @return New object created using object's of type {@link U} attributes.
     */
    T toEntityModel(U domainModel);

    T toEntityModel(@MappingTarget T entityModel, U domainModel);
}

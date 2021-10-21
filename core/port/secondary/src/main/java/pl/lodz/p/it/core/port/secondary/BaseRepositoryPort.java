package pl.lodz.p.it.core.port.secondary;

import pl.lodz.p.it.core.shared.exception.core.BaseException;

import java.util.List;
import java.util.Optional;

/**
 * Base interface providing common CRUD methods.
 *
 * @param <U> Model object that will be handled via this interface
 */
public interface BaseRepositoryPort<U> {

    /**
     * Method responsible for finding object of a generic type with given key.
     *
     * @param key Object's business identifier
     * @return Object with provided key
     * @throws BaseException Thrown in case of non-existent object
     */
    Optional<U> find(String key) throws BaseException;

    /**
     * Method responsible for finding list of objects of a generic type.
     *
     * @return List of objects
     */
    List<U> findAll() throws BaseException;

    /**
     * Method responsible for saving provided object.
     *
     * @param u Newly created object
     * @return Created object
     */
    U save(U u);

    /**
     * Method responsible for updating existing object.
     *
     * @param key Object's business identifier that is meant to be updated
     * @param u   Updated object's body
     * @return Updated object
     * @throws BaseException Thrown in case of providing a non-existent object
     */
    U update(String key, U u) throws BaseException;

    /**
     * Method responsible for deleting object with given key.
     *
     * @param key Object's business identifier
     * @throws BaseException Thrown in case of providing a non-existent object
     */
    void delete(String key) throws BaseException;
}

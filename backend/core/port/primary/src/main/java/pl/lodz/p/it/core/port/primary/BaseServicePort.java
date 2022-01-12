package pl.lodz.p.it.core.port.primary;

import pl.lodz.p.it.core.domain.BaseModel;
import pl.lodz.p.it.core.shared.exception.core.BaseException;

import javax.validation.Valid;
import java.util.List;

/**
 * Base interface providing common CRUD methods.
 *
 * @param <U> Model object that will be handled via this interface
 */
public interface BaseServicePort<U extends BaseModel> {

    /**
     * Method responsible for finding object of a generic type with given key.
     *
     * @param key Object's business identifier
     * @return Object with provided key
     */
    U find(String key);

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
    U save(@Valid U u);

    /**
     * Method responsible for updating existing object.
     *
     * @param key Object's business identifier that is meant to be updated
     * @param u   Updated object's body
     * @return Updated object
     */
    U update(String key, @Valid U u);

    /**
     * Method responsible for deleting object with given key.
     *
     * @param key Object's business identifier
     */
    void delete(String key);
}

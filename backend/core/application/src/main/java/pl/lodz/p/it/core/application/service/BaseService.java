package pl.lodz.p.it.core.application.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pl.lodz.p.it.core.domain.BaseModel;
import pl.lodz.p.it.core.port.primary.BaseServicePort;
import pl.lodz.p.it.core.port.secondary.BaseRepositoryPort;

/**
 * Implementation of base methods provided by the {@link BaseServicePort} interface used by
 * client-side adapters.
 *
 * @param <T> Type of appropriate domain model
 */
@AllArgsConstructor
@Validated
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public abstract class BaseService<T extends BaseModel> implements BaseServicePort<T> {

    protected BaseRepositoryPort<T> repository;

    @Override
    public T find(String key) {
        return repository.find(key);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T save(@Valid T t) {
        return repository.save(t);
    }

    @Override
    public T update(String key, @Valid T t) {
        return repository.update(key, t);
    }

    @Override
    public void delete(String key) {
        repository.delete(key);
    }
}

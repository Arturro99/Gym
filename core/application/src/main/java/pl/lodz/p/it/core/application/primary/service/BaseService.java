package pl.lodz.p.it.core.application.primary.service;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import pl.lodz.p.it.core.port.primary.BaseServicePort;
import pl.lodz.p.it.core.port.secondary.BaseRepositoryPort;

import javax.validation.Valid;
import java.util.List;

/**
 * Implementation of base methods provided by the {@link BaseServicePort} interface used by client-side adapters.
 *
 * @param <T> Type of appropriate domain model
 */
@AllArgsConstructor
@Validated
public abstract class BaseService<T> implements BaseServicePort<T> {

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

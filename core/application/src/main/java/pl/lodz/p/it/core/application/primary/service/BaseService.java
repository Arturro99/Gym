package pl.lodz.p.it.core.application.primary.service;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.core.shared.BasePort;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of base methods provided by the {@link BasePort} interface used by client-side adapters.
 *
 * @param <T> Type of appropriate domain model
 */
@NoArgsConstructor
public abstract class BaseService<T> implements BasePort<T> {

    protected BasePort<T> repository;

    @Override
    public Optional<T> find(String key) {
        return repository.find(key);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public T update(String key, T t) {
        return repository.update(key, t);
    }

    @Override
    public void delete(String key) {
        repository.delete(key);
    }
}

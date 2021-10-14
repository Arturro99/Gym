package pl.lodz.p.it.core.application.secondary.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.core.application.secondary.mapper.BaseMapper;
import pl.lodz.p.it.core.shared.BasePort;
import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.repositoryhibernate.repository.BaseRepository;

/**
 * Implementation of base methods provided by the {@link BasePort} interface.
 *
 * @param <T> Type of appropriate entity
 * @param <U> Type of appropriate domain model
 */
@NoArgsConstructor
public abstract class BaseService<T, U> implements BasePort<U> {

    protected BaseRepository<T> repository;

    protected BaseMapper<T, U> mapper;

    @Override
    public Optional<U> find(String key) {
        return repository.findByBusinessId(key)
            .map(mapper::toDomainModel);
    }

    @Override
    public List<U> findAll() {
        return repository.findAll().stream()
            .map(mapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public U save(U u) {
        T entity = mapper.toEntityModel(u);
        T savedEntity = repository.save(entity);
        return mapper.toDomainModel(savedEntity);
    }

    @Override
    public U update(String key, U u) {
        T entity = repository.findByBusinessId(key).orElseThrow(
            BaseException::notFoundException);
        T updated = mapper
            .toEntityModel(entity, u);
        T response = repository.save(updated);
        return mapper.toDomainModel(response);
    }

    @Override
    public void delete(String key) {
        T entity = repository.findByBusinessId(key)
            .orElseThrow(BaseException::notFoundException);
        repository.delete(entity);
    }
}

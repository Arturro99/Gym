package pl.lodz.p.it.repositoryhibernate.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.repositoryhibernate.mapper.BaseMapper;
import pl.lodz.p.it.core.domain.BaseModel;
import pl.lodz.p.it.core.port.secondary.BaseRepositoryPort;
import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.repositoryhibernate.entity.BaseEntity;
import pl.lodz.p.it.repositoryhibernate.repository.BaseRepository;

/**
 * Implementation of base methods provided by the {@link BaseRepositoryPort} interface used by
 * repository-side adapters.
 *
 * @param <T> Type of appropriate entity
 * @param <U> Type of appropriate domain model
 */
@AllArgsConstructor
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED, timeout = 3)
@Retryable(exclude = BaseException.class, maxAttemptsExpression = "${retry.maxAttempts}",
    backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
public abstract class BaseRepositoryService<T extends BaseEntity, U extends BaseModel> implements
    BaseRepositoryPort<U> {

    protected BaseRepository<T> repository;

    protected BaseMapper<T, U> mapper;

    @Override
    public U find(String key) {
        return repository.findByBusinessId(key)
            .map(mapper::toDomainModel).orElseThrow(BaseException::notFoundException);
    }

    @Override
    public List<U> findAll() {
        return repository.findAll().stream()
            .map(mapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public U save(U u) {
        T entity = repository.instantiate();
        entity = mapper.toEntityModel(entity, u);
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

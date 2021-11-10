package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.core.application.secondary.mapper.DietMapper;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.core.port.secondary.DietRepositoryPort;
import pl.lodz.p.it.core.shared.exception.DietException;
import pl.lodz.p.it.core.shared.exception.DietTypeException;
import pl.lodz.p.it.repositoryhibernate.entity.DietEntity;
import pl.lodz.p.it.repositoryhibernate.entity.DietTypeEntity;
import pl.lodz.p.it.repositoryhibernate.repository.DietRepository;
import pl.lodz.p.it.repositoryhibernate.repository.DietTypeRepository;

/**
 * Service class responsible for operating on diet repository.
 */
@Service
public class DietRepositoryService extends BaseRepositoryService<DietEntity, Diet> implements DietRepositoryPort {

    private final DietTypeRepository dietTypeRepository;

    @Autowired
    public DietRepositoryService(DietRepository repository, DietMapper mapper, DietTypeRepository dietTypeRepository) {
        super(repository, mapper);
        this.dietTypeRepository = dietTypeRepository;
    }

    @Override
    public Diet find(String key) {
        return repository.findByBusinessId(key)
                .map(mapper::toDomainModel).orElseThrow(DietException::dietNotFoundException);
    }

    @Override
    public Diet save(Diet diet) {
        DietEntity entity = repository.instantiate();
        entity = mapper.toEntityModel(entity, diet);

        DietTypeEntity dietType = dietTypeRepository.findByBusinessId(
                diet.getDietType().getName()).orElseThrow(DietTypeException::dietTypeNotFoundException);
        entity.setDietType(dietType);

        DietEntity saved = repository.save(entity);

        return mapper.toDomainModel(saved);
    }

    @Override
    public Diet update(String key, Diet diet) {
        DietEntity entity = repository.findByBusinessId(key).orElseThrow(
                DietException::dietNotFoundException);
        DietEntity updated = mapper
                .toEntityModel(entity, diet);

        DietEntity response = repository.save(updated);
        return mapper.toDomainModel(response);
    }

    @Override
    public void delete(String key) {
        DietEntity entity = repository.findByBusinessId(key)
                .orElseThrow(DietException::dietNotFoundException);
        if (!entity.getAccounts().isEmpty()) {
            throw DietException.dietConflictException();
        }
        repository.delete(entity);
    }
}

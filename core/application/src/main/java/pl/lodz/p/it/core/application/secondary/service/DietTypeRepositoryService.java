package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.DietTypeMapper;
import pl.lodz.p.it.core.domain.DietType;
import pl.lodz.p.it.core.port.secondary.DietTypeRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.DietTypeEntity;
import pl.lodz.p.it.repositoryhibernate.repository.DietTypeRepository;

/**
 * Service class responsible for operating on diet type repository.
 */
@Component
public class DietTypeRepositoryService extends BaseRepositoryService<DietTypeEntity, DietType> implements
        DietTypeRepositoryPort {

    @Autowired
    public DietTypeRepositoryService(DietTypeRepository repository, DietTypeMapper mapper) {
        super(repository, mapper);
    }
}

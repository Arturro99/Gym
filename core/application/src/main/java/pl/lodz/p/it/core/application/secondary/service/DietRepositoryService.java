package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.DietMapper;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.core.port.secondary.DietRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.DietEntity;
import pl.lodz.p.it.repositoryhibernate.repository.DietRepository;

/**
 * Service class responsible for operating on diet repository.
 */
@Component
public class DietRepositoryService extends BaseRepositoryService<DietEntity, Diet> implements DietRepositoryPort {

    @Autowired
    public DietRepositoryService(DietRepository repository, DietMapper mapper) {
        super(repository, mapper);
    }
}

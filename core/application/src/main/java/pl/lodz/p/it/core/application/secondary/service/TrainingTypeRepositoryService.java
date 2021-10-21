package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.TrainingTypeMapper;
import pl.lodz.p.it.core.domain.TrainingType;
import pl.lodz.p.it.core.port.secondary.TrainingTypeRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingTypeEntity;
import pl.lodz.p.it.repositoryhibernate.repository.TrainingTypeRepository;

/**
 * Service class responsible for operating on training type repository.
 */
@Component
public class TrainingTypeRepositoryService extends BaseRepositoryService<TrainingTypeEntity, TrainingType> implements
        TrainingTypeRepositoryPort {

    @Autowired
    public TrainingTypeRepositoryService(TrainingTypeRepository repository, TrainingTypeMapper mapper) {
        super(repository, mapper);
    }
}

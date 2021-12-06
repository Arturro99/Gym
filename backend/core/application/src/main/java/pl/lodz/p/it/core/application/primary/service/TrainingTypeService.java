package pl.lodz.p.it.core.application.primary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.TrainingType;
import pl.lodz.p.it.core.port.primary.TrainingTypeServicePort;
import pl.lodz.p.it.core.port.secondary.TrainingTypeRepositoryPort;

/**
 * Service class responsible for operating on training type objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public class TrainingTypeService extends BaseService<TrainingType> implements
        TrainingTypeServicePort {

    @Autowired
    public TrainingTypeService(TrainingTypeRepositoryPort trainingTypeRepositoryPort) {
        super(trainingTypeRepositoryPort);
    }
}

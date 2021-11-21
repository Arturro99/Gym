package pl.lodz.p.it.core.application.primary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.DietType;
import pl.lodz.p.it.core.port.primary.DietTypeServicePort;
import pl.lodz.p.it.core.port.secondary.DietTypeRepositoryPort;

/**
 * Service class responsible for operating on diet type objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public class DietTypeService extends BaseService<DietType> implements
    DietTypeServicePort {

    @Autowired
    public DietTypeService(DietTypeRepositoryPort dietTypeRepositoryPort) {
        super(dietTypeRepositoryPort);
    }
}

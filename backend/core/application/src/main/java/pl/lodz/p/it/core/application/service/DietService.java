package pl.lodz.p.it.core.application.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.core.port.primary.DietServicePort;
import pl.lodz.p.it.core.port.secondary.DietRepositoryPort;

/**
 * Service class responsible for operating on diet objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public class DietService extends BaseService<Diet> implements DietServicePort {

    @Autowired
    public DietService(DietRepositoryPort dietRepositoryPort) {
        super(dietRepositoryPort);
    }
}

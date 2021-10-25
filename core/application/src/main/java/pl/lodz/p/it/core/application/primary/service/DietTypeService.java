package pl.lodz.p.it.core.application.primary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.DietType;
import pl.lodz.p.it.core.port.primary.DietTypeServicePort;
import pl.lodz.p.it.core.port.secondary.DietTypeRepositoryPort;

/**
 * Service class responsible for operating on diet type objects.
 */
@Component
public class DietTypeService extends BaseService<DietType> implements
        DietTypeServicePort {

    @Autowired
    public DietTypeService(DietTypeRepositoryPort dietTypeRepositoryPort) {
        super(dietTypeRepositoryPort);
    }
}

package pl.lodz.p.it.core.application.primary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.DietType;
import pl.lodz.p.it.core.port.primary.DietTypeServicePort;
import pl.lodz.p.it.core.port.secondary.DietTypeRepositoryPort;

/**
 * Service class responsible for operating on diet type objects.
 */
@Component
@AllArgsConstructor
public class DietTypeService extends BaseService<DietType> implements
        DietTypeServicePort {

    private final DietTypeRepositoryPort dietTypeRepositoryPort;

}

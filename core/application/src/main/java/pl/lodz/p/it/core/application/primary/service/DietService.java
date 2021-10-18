package pl.lodz.p.it.core.application.primary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.core.port.primary.DietServicePort;
import pl.lodz.p.it.core.port.secondary.DietRepositoryPort;

/**
 * Service class responsible for operating on diet objects.
 */
@Component
@AllArgsConstructor
public class DietService extends BaseService<Diet> implements DietServicePort {

    private final DietRepositoryPort dietRepositoryPort;

}

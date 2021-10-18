package pl.lodz.p.it.core.application.primary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.TrainingType;
import pl.lodz.p.it.core.port.primary.TrainingTypeServicePort;
import pl.lodz.p.it.core.port.secondary.TrainingTypeRepositoryPort;

/**
 * Service class responsible for operating on training type objects.
 */
@Component
@AllArgsConstructor
public class TrainingTypeService extends BaseService<TrainingType> implements
        TrainingTypeServicePort {

    private final TrainingTypeRepositoryPort trainingTypeRepositoryPort;
}

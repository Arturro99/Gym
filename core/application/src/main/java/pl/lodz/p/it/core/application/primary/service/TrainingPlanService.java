package pl.lodz.p.it.core.application.primary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.core.port.primary.TrainingPlanServicePort;
import pl.lodz.p.it.core.port.secondary.TrainingPlanRepositoryPort;

/**
 * Service class responsible for operating on training plan objects.
 */
@Component
@AllArgsConstructor
public class TrainingPlanService extends BaseService<TrainingPlan> implements
        TrainingPlanServicePort {

    private final TrainingPlanRepositoryPort trainingPlanRepositoryPort;

}

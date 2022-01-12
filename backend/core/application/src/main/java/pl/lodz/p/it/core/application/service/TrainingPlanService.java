package pl.lodz.p.it.core.application.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.core.port.primary.TrainingPlanServicePort;
import pl.lodz.p.it.core.port.secondary.TrainingPlanRepositoryPort;

/**
 * Service class responsible for operating on training plan objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public class TrainingPlanService extends BaseService<TrainingPlan> implements
    TrainingPlanServicePort {

    @Autowired
    public TrainingPlanService(TrainingPlanRepositoryPort trainingPlanRepositoryPort) {
        super(trainingPlanRepositoryPort);
    }
}

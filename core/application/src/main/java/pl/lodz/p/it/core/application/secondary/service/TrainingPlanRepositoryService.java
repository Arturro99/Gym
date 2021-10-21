package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.TrainingPlanMapper;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.core.port.secondary.TrainingPlanRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;
import pl.lodz.p.it.repositoryhibernate.repository.TrainingPlanRepository;

/**
 * Service class responsible for operating on training plan repository.
 */
@Component
public class TrainingPlanRepositoryService extends BaseRepositoryService<TrainingPlanEntity, TrainingPlan> implements
        TrainingPlanRepositoryPort {

    @Autowired
    public TrainingPlanRepositoryService(TrainingPlanRepository repository, TrainingPlanMapper mapper) {
        super(repository, mapper);
    }
}

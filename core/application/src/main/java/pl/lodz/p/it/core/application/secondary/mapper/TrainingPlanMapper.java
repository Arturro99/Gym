package pl.lodz.p.it.core.application.secondary.mapper;

import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;

/**
 * Interface responsible for mapping {@link TrainingPlan} objects and {@link TrainingPlanEntity}
 */
public interface TrainingPlanMapper extends BaseMapper<AccessLevelEntity, AccessLevel> {

}

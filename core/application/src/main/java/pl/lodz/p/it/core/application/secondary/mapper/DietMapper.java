package pl.lodz.p.it.core.application.secondary.mapper;

import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.DietEntity;

/**
 * Interface responsible for mapping {@link Diet} objects and {@link DietEntity}
 */
public interface DietMapper extends BaseMapper<AccessLevelEntity, AccessLevel> {

}

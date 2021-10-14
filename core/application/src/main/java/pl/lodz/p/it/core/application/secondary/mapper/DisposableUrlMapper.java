package pl.lodz.p.it.core.application.secondary.mapper;

import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.DisposableUrlEntity;

/**
 * Interface responsible for mapping {@link DisposableUrl} objects and {@link DisposableUrlEntity}
 */
public interface DisposableUrlMapper extends BaseMapper<AccessLevelEntity, AccessLevel> {

}

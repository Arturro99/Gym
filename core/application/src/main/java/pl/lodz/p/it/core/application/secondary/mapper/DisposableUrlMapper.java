package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.repositoryhibernate.entity.DisposableUrlEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link DisposableUrl} objects and {@link DisposableUrlEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DisposableUrlMapper implements BaseMapper<DisposableUrlEntity, DisposableUrl> {

}

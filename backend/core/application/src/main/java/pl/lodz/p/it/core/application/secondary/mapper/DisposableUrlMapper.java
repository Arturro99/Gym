package pl.lodz.p.it.core.application.secondary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.repositoryhibernate.entity.DisposableUrlEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Interface responsible for mapping {@link DisposableUrl} objects and {@link DisposableUrlEntity}
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DisposableUrlMapper extends BaseMapper<DisposableUrlEntity, DisposableUrl> {

    @Override
    @Mapping(source = "businessId", target = "url")
    @Mapping(source = "modifiedBy.businessId", target = "modifiedBy")
    @Mapping(source = "createdBy.businessId", target = "createdBy")
    @Mapping(source = "account.businessId", target = "account")
    DisposableUrl toDomainModel(DisposableUrlEntity entityModel);

    @Override
    @Mapping(source = "url", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "account", target = "account.businessId")
    DisposableUrlEntity toEntityModel(DisposableUrl domainModel);

    @Override
    @Mapping(source = "url", target = "businessId")
    @Mapping(source = "modifiedBy", target = "modifiedBy.businessId")
    @Mapping(source = "createdBy", target = "createdBy.businessId")
    @Mapping(source = "account", target = "account.businessId")
    DisposableUrlEntity toEntityModel(@MappingTarget DisposableUrlEntity entityModel, DisposableUrl domainModel);
}

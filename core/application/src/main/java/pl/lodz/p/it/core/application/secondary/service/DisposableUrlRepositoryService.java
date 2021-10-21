package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.DisposableUrlMapper;
import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.core.port.secondary.DisposableUrlRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.DisposableUrlEntity;
import pl.lodz.p.it.repositoryhibernate.repository.DisposableUrlRepository;

/**
 * Service class responsible for operating on disposable url repository.
 */
@Component
public class DisposableUrlRepositoryService extends BaseRepositoryService<DisposableUrlEntity, DisposableUrl> implements
        DisposableUrlRepositoryPort {

    @Autowired
    public DisposableUrlRepositoryService(DisposableUrlRepository repository, DisposableUrlMapper mapper) {
        super(repository, mapper);
    }
}

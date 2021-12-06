package pl.lodz.p.it.core.application.primary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.core.port.primary.DisposableUrlServicePort;
import pl.lodz.p.it.core.port.secondary.DisposableUrlRepositoryPort;

/**
 * Service class responsible for operating on disposable url objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public class DisposableUrlService extends BaseService<DisposableUrl> implements
    DisposableUrlServicePort {

    @Autowired
    public DisposableUrlService(DisposableUrlRepositoryPort disposableUrlRepositoryPort) {
        super(disposableUrlRepositoryPort);
    }
}

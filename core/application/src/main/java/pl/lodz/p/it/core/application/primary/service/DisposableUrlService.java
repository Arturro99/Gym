package pl.lodz.p.it.core.application.primary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.core.port.primary.DisposableUrlServicePort;
import pl.lodz.p.it.core.port.secondary.DisposableUrlRepositoryPort;

/**
 * Service class responsible for operating on disposable url objects.
 */
@Component
public class DisposableUrlService extends BaseService<DisposableUrl> implements
        DisposableUrlServicePort {

    @Autowired
    public DisposableUrlService(DisposableUrlRepositoryPort disposableUrlRepositoryPort) {
        super(disposableUrlRepositoryPort);
    }
}

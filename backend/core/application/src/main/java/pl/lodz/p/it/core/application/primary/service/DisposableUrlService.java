package pl.lodz.p.it.core.application.primary.service;

import static java.time.temporal.ChronoUnit.HOURS;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.core.port.primary.DisposableUrlServicePort;
import pl.lodz.p.it.core.port.secondary.DisposableUrlRepositoryPort;
import pl.lodz.p.it.core.shared.constant.UrlAction;

/**
 * Service class responsible for operating on disposable url objects.
 */
@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
public class DisposableUrlService extends BaseService<DisposableUrl> implements
    DisposableUrlServicePort {

    @Value("${url.expiration.time}")
    int urlExpirationTime;

    @Autowired
    public DisposableUrlService(DisposableUrlRepositoryPort disposableUrlRepositoryPort) {
        super(disposableUrlRepositoryPort);
    }

    @Override
    @Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
    public void createDisposableUrl(String url, String login, UrlAction actionType) {
        DisposableUrl disposableUrl = DisposableUrl.builder()
            .url(url)
            .account(login)
            .actionType(actionType.name())
            .expireDate(OffsetDateTime.now().plus(urlExpirationTime, HOURS))
            .build();

        repository.save(disposableUrl);
    }
}

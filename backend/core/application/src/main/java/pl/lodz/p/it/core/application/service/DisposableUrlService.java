package pl.lodz.p.it.core.application.service;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.core.port.primary.DisposableUrlServicePort;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.port.secondary.DisposableUrlRepositoryPort;
import pl.lodz.p.it.core.shared.constant.UrlAction;
import pl.lodz.p.it.core.shared.exception.DisposableUrlException;

import java.time.OffsetDateTime;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * Service class responsible for operating on disposable url objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
@FieldDefaults(level = PRIVATE)
public class DisposableUrlService extends BaseService<DisposableUrl> implements
    DisposableUrlServicePort {

    final AccountRepositoryPort accountRepositoryPort;

    @Value("${url.expiration.time}")
    int urlExpirationTime;

    @Autowired
    public DisposableUrlService(DisposableUrlRepositoryPort disposableUrlRepositoryPort,
                                AccountRepositoryPort accountRepositoryPort) {
        super(disposableUrlRepositoryPort);
        this.accountRepositoryPort = accountRepositoryPort;
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

    @Override
    public void confirmRegistration(String token) {
        DisposableUrl disposableUrl;
        disposableUrl = repository.find(token);

        if (disposableUrl.getExpireDate().isBefore(OffsetDateTime.now())) {
            throw DisposableUrlException.urlExpiredException();
        }

        Account account = accountRepositoryPort.find(disposableUrl.getAccount());
        account.setConfirmed(true);
        disposableUrl.setExpireDate(OffsetDateTime.now().minus(1, MINUTES));

        accountRepositoryPort.update(account.getLogin(), account);
        repository.update(token, disposableUrl);
    }
}

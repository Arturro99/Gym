package pl.lodz.p.it.repositoryhibernate;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.mapper.AccountMapper;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static javax.persistence.FlushModeType.AUTO;
import static javax.persistence.FlushModeType.COMMIT;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Component
@AllArgsConstructor
@Transactional(propagation = SUPPORTS)
public class SystemLoggedInUserAuditorAware implements AuditorAware<AccountEntity> {

    AccountRepository accountRepository;
    EntityManager entityManager;
    AccountMapper accountMapper;

    @Override
    public Optional<AccountEntity> getCurrentAuditor() {
        entityManager.setFlushMode(COMMIT);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Optional.ofNullable(authentication).isPresent() && !authentication.getPrincipal().equals("anonymousUser")) {
            String currentPrincipalName = authentication.getName();
            Optional<AccountEntity> account = accountRepository.findByBusinessId(currentPrincipalName);
            entityManager.setFlushMode(AUTO);
            return account;
        }
        return Optional.empty();
    }
}

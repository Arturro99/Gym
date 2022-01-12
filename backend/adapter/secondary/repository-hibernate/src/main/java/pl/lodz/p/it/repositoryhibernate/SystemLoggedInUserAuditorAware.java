package pl.lodz.p.it.repositoryhibernate;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.mapper.AccountMapper;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;

@Component
@AllArgsConstructor
@Transactional(propagation = SUPPORTS)
public class SystemLoggedInUserAuditorAware implements AuditorAware<AccountEntity> {

    AccountRepository accountRepository;
    AccountMapper accountMapper;

    @Override
    public Optional<AccountEntity> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Optional.ofNullable(authentication).isPresent()) {
            String currentPrincipalName = authentication.getName();
            return accountRepository.findByBusinessId(currentPrincipalName);
        }
        return Optional.empty();
    }
}

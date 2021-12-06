package pl.lodz.p.it.core.application.secondary;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.AccountMapper;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SystemLoggedInUserAuditorAware implements AuditorAware<AccountEntity> {

    AccountRepository accountRepository;
    AccountMapper accountMapper;

    @Override
    public Optional<AccountEntity> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return accountRepository.findByBusinessId(currentPrincipalName);
    }
}

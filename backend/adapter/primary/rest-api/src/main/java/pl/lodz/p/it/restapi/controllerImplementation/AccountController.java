package pl.lodz.p.it.restapi.controllerImplementation;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.core.port.primary.mailing.OnRegistrationCompleteEvent;
import pl.lodz.p.it.restapi.controller.AccountsApiDelegate;
import pl.lodz.p.it.restapi.dto.AccountDetailsResponse;
import pl.lodz.p.it.restapi.dto.AccountGeneralResponse;
import pl.lodz.p.it.restapi.dto.AccountRequestPost;
import pl.lodz.p.it.restapi.dto.AccountRequestPut;
import pl.lodz.p.it.restapi.dto.AccountRequestPutObject;
import pl.lodz.p.it.restapi.dto.DietResponse;
import pl.lodz.p.it.restapi.dto.TrainingPlanResponse;
import pl.lodz.p.it.restapi.mapper.account.AccountDetailsResponseMapper;
import pl.lodz.p.it.restapi.mapper.account.AccountGeneralResponseMapper;
import pl.lodz.p.it.restapi.mapper.account.AccountRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.account.AccountRequestPutMapper;
import pl.lodz.p.it.restapi.mapper.diet.DietResponseMapper;
import pl.lodz.p.it.restapi.mapper.training.TrainingPlanResponseMapper;

@RestController
@AllArgsConstructor
public class AccountController implements AccountsApiDelegate {

    private final AccountServicePort accountServicePort;

    private final AccountGeneralResponseMapper accountGeneralResponseMapper;

    private final AccountDetailsResponseMapper accountDetailsResponseMapper;

    private final AccountRequestPostMapper accountRequestPostMapper;

    private final AccountRequestPutMapper accountRequestPutMapper;

    private final PasswordEncoder passwordEncoder;

    private final DietResponseMapper dietResponseMapper;

    private final TrainingPlanResponseMapper trainingPlanResponseMapper;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public ResponseEntity<AccountDetailsResponse> getAccount(String login) {
        return ResponseEntity.ok(
            accountDetailsResponseMapper.toDtoModel(
                accountServicePort.find(login)));
    }

    @Override
    public ResponseEntity<List<AccountGeneralResponse>> getAccounts() {
        return ResponseEntity.ok(
            accountServicePort.findAll().stream()
                .map(accountGeneralResponseMapper::toDtoModel)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
    public ResponseEntity<AccountGeneralResponse> createAccount(AccountRequestPost accountRequest) {
        accountRequest.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        Account account = accountRequestPostMapper.toDomainModel(accountRequest);
        Account saved = accountServicePort.save(account);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(saved));
        return ResponseEntity.ok(accountGeneralResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<AccountDetailsResponse> updateAccount(String login,
        AccountRequestPut accountRequestPut) {
        Account account = accountRequestPutMapper.toDomainModel(accountRequestPut);
        Account updated = accountServicePort.update(login, account);
        return ResponseEntity.ok(accountDetailsResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<AccountDetailsResponse> addTrainingPlan(String login,
        AccountRequestPutObject accountRequestPutObject) {
        Account updated = accountServicePort
            .addTrainingPlan(login, accountRequestPutObject.getNumber());
        return ResponseEntity.ok(accountDetailsResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<Void> deleteTrainingPlan(String number, String login) {
        accountServicePort.removeTrainingPlan(login, number);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AccountDetailsResponse> addDiet(String login,
        AccountRequestPutObject accountRequestPutObject) {
        Account updated = accountServicePort.addDiet(login, accountRequestPutObject.getNumber());
        return ResponseEntity.ok(accountDetailsResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<Void> deleteDiet(String number, String login) {
        accountServicePort.removeDiet(login, number);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<DietResponse>> getDietsByAccountLogin(String login) {
        return ResponseEntity.ok(accountServicePort.getDietsByAccountLogin(login).stream()
            .map(dietResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<TrainingPlanResponse>> getTrainingPlansByAccountLogin(String login) {
        return ResponseEntity.ok(accountServicePort.getTrainingPlansByAccountLogin(login).stream()
            .map(trainingPlanResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }
}

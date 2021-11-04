package pl.lodz.p.it.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.restapi.dto.AccountDetailsResponse;
import pl.lodz.p.it.restapi.dto.AccountGeneralResponse;
import pl.lodz.p.it.restapi.dto.AccountRequestPost;
import pl.lodz.p.it.restapi.dto.AccountRequestPutObject;
import pl.lodz.p.it.restapi.mapper.account.AccountDetailsResponseMapper;
import pl.lodz.p.it.restapi.mapper.account.AccountGeneralResponseMapper;
import pl.lodz.p.it.restapi.mapper.account.AccountRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.account.AccountRequestPutObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AccountController implements AccountsApiDelegate {

    private final AccountServicePort accountServicePort;

    private final AccountGeneralResponseMapper accountGeneralResponseMapper;

    private final AccountDetailsResponseMapper accountDetailsResponseMapper;

    private final AccountRequestPostMapper accountRequestPostMapper;

    private final AccountRequestPutObjectMapper accountRequestPutObjectMapper;

    @Override
    public ResponseEntity<AccountGeneralResponse> createAccount(AccountRequestPost accountRequest) {
        return ResponseEntity.ok(
                accountGeneralResponseMapper.toDtoModel(
                        accountServicePort.save(accountRequestPostMapper.toDomainModel(accountRequest)))
        );
    }

    @Override
    public ResponseEntity<List<AccountGeneralResponse>> getAccounts() {
        return ResponseEntity.ok(
                accountServicePort.findAll().stream()
                        .map(accountGeneralResponseMapper::toDtoModel)
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<AccountDetailsResponse> getAccount(String login) {
        return ResponseEntity.ok(
                accountDetailsResponseMapper.toDtoModel(
                        accountServicePort.find(login)));
    }

    @Override
    public ResponseEntity<AccountDetailsResponse> addTrainingPlan(String login, AccountRequestPutObject accountRequestPutObject) {
        Account updated = accountServicePort.addTrainingPlan(login, accountRequestPutObject.getNumber());
        return ResponseEntity.ok(accountDetailsResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<Void> deleteTrainingPlan(String number, String login) {
        accountServicePort.removeTrainingPlan(login, number);
        return ResponseEntity.ok().build();
    }
}

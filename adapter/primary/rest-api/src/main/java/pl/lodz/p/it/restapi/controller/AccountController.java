package pl.lodz.p.it.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.restapi.dto.AccountDetailsResponse;
import pl.lodz.p.it.restapi.dto.AccountGeneralResponse;
import pl.lodz.p.it.restapi.dto.AccountRequest;
import pl.lodz.p.it.restapi.mapper.AccountDetailsResponseMapper;
import pl.lodz.p.it.restapi.mapper.AccountGeneralResponseMapper;
import pl.lodz.p.it.restapi.mapper.AccountRequestMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AccountController implements AccountsApiDelegate {

    private final AccountServicePort accountServicePort;

    private final AccountGeneralResponseMapper accountGeneralResponseMapper;

    private final AccountDetailsResponseMapper accountDetailsResponseMapper;

    private final AccountRequestMapper accountRequestMapper;

    @Override
    public ResponseEntity<AccountGeneralResponse> createAccount(AccountRequest accountRequest) {
        return ResponseEntity.ok(
                accountGeneralResponseMapper.toDtoModel(
                        accountServicePort.save(accountRequestMapper.toDomainModel(accountRequest)))
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
}

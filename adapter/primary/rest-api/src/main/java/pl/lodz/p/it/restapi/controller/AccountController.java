package pl.lodz.p.it.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.restapi.dto.AccountGeneralResponse;
import pl.lodz.p.it.restapi.dto.AccountRequest;
import pl.lodz.p.it.restapi.mapper.AccountRestMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AccountController implements AccountsApiDelegate {

    private final AccountServicePort accountServicePort;

    private final AccountRestMapper accountRestMapper;

    @Override
    public ResponseEntity<Void> createAccount(AccountRequest accountRequest) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<AccountGeneralResponse>> getAccounts() {
        return ResponseEntity.ok(
                accountServicePort.findAll().stream()
                        .map(accountRestMapper::toDtoModel)
                        .collect(Collectors.toList()));
    }
}

package pl.lodz.p.it.restapi.controllerImplementation;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.primary.AccessLevelServicePort;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.restapi.controller.AccessLevelsApiDelegate;
import pl.lodz.p.it.restapi.dto.AccessLevelRequestPost;
import pl.lodz.p.it.restapi.dto.AccessLevelResponse;
import pl.lodz.p.it.restapi.mapper.accessLevel.AccessLevelRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.accessLevel.AccessLevelResponseMapper;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccessLevelController implements AccessLevelsApiDelegate {

    AccessLevelServicePort accessLevelServicePort;

    AccessLevelResponseMapper accessLevelResponseMapper;

    AccessLevelRequestPostMapper accessLevelRequestPostMapper;

    AccountServicePort accountServicePort;

    @Override
    public ResponseEntity<List<AccessLevelResponse>> getAccessLevels() {
        return ResponseEntity.ok(accessLevelServicePort.findAll().stream()
            .map(accessLevelResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<AccessLevelResponse>> getAccessLevelsByClientLogin(String login) {
        Account account = accountServicePort.find(login);
        return ResponseEntity.ok(accessLevelServicePort.findByAccount(account).stream()
            .map(accessLevelResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<AccessLevelResponse> createAccessLevel(
        AccessLevelRequestPost accessLevelRequestPost) {
        AccessLevel accessLevel = accessLevelRequestPostMapper
            .toDomainModel(accessLevelRequestPost);
        AccessLevel saved = accessLevelServicePort.save(accessLevel);
        return ResponseEntity.ok(accessLevelResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<Void> deleteAccessLevel(String login, String level) {
        accessLevelServicePort.removeAccessLevel(login, level);
        return ResponseEntity.ok().build();
    }
}

package pl.lodz.p.it.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.port.primary.AccessLevelServicePort;
import pl.lodz.p.it.restapi.dto.AccessLevelRequestPost;
import pl.lodz.p.it.restapi.dto.AccessLevelResponse;
import pl.lodz.p.it.restapi.mapper.accessLevel.AccessLevelRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.accessLevel.AccessLevelResponseMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AccessLevelController implements AccessLevelsApiDelegate {

    private final AccessLevelServicePort accessLevelServicePort;

    private final AccessLevelResponseMapper accessLevelResponseMapper;

    private final AccessLevelRequestPostMapper accessLevelRequestPostMapper;

    @Override
    public ResponseEntity<List<AccessLevelResponse>> getAccessLevels() {
        return ResponseEntity.ok(accessLevelServicePort.findAll().stream()
                .map(accessLevelResponseMapper::toDtoModel)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<AccessLevelResponse> createAccessLevel(AccessLevelRequestPost accessLevelRequestPost) {
        AccessLevel accessLevel = accessLevelRequestPostMapper.toDomainModel(accessLevelRequestPost);
        AccessLevel saved = accessLevelServicePort.save(accessLevel);
        return ResponseEntity.ok(accessLevelResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<Void> deleteAccessLevel(String login, String level) {
        accessLevelServicePort.removeAccessLevel(login, level);
        return ResponseEntity.ok().build();
    }
}

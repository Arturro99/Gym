package pl.lodz.p.it.restapi.controllerImplementation;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.port.primary.DietTypeServicePort;
import pl.lodz.p.it.restapi.controller.DietTypesApiDelegate;
import pl.lodz.p.it.restapi.dto.DietTypeResponse;
import pl.lodz.p.it.restapi.mapper.diet.DietTypeResponseMapper;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DietTypeController implements DietTypesApiDelegate {

    DietTypeServicePort dietTypeServicePort;

    DietTypeResponseMapper dietTypeResponseMapper;

    @Override
    public ResponseEntity<List<DietTypeResponse>> getDietTypes() {
        return ResponseEntity.ok(dietTypeServicePort.findAll().stream()
            .map(dietTypeResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }
}

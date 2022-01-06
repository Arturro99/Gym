package pl.lodz.p.it.restapi.controllerImplementation;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.port.primary.TrainingTypeServicePort;
import pl.lodz.p.it.restapi.controller.TrainingTypesApiDelegate;
import pl.lodz.p.it.restapi.dto.TrainingTypeResponse;
import pl.lodz.p.it.restapi.mapper.training.TrainingTypeResponseMapper;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TrainingTypeController implements TrainingTypesApiDelegate {

    TrainingTypeServicePort trainingTypeServicePort;

    TrainingTypeResponseMapper trainingTypeResponseMapper;

    @Override
    public ResponseEntity<List<TrainingTypeResponse>> getTrainingTypes() {
        return ResponseEntity.ok(trainingTypeServicePort.findAll().stream()
            .map(trainingTypeResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }
}

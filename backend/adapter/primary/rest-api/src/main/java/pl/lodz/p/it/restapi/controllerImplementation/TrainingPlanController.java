package pl.lodz.p.it.restapi.controllerImplementation;

import static lombok.AccessLevel.PRIVATE;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.core.port.primary.TrainingPlanServicePort;
import pl.lodz.p.it.restapi.controller.TrainingPlansApiDelegate;
import pl.lodz.p.it.restapi.dto.TrainingPlanDetailsResponse;
import pl.lodz.p.it.restapi.dto.TrainingPlanRequestPost;
import pl.lodz.p.it.restapi.dto.TrainingPlanRequestPut;
import pl.lodz.p.it.restapi.dto.TrainingPlanResponse;
import pl.lodz.p.it.restapi.mapper.training.TrainingPlanDetailsResponseMapper;
import pl.lodz.p.it.restapi.mapper.training.TrainingPlanRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.training.TrainingPlanRequestPutMapper;
import pl.lodz.p.it.restapi.mapper.training.TrainingPlanResponseMapper;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TrainingPlanController implements TrainingPlansApiDelegate {

    TrainingPlanServicePort trainingPlanServicePort;

    TrainingPlanResponseMapper trainingPlanResponseMapper;

    TrainingPlanDetailsResponseMapper trainingPlanDetailsResponseMapper;

    TrainingPlanRequestPostMapper trainingPlanRequestPostMapper;

    TrainingPlanRequestPutMapper trainingPlanRequestPutMapper;

    @Override
    public ResponseEntity<TrainingPlanDetailsResponse> getTrainingPlan(String number) {
        return ResponseEntity
            .ok(trainingPlanDetailsResponseMapper.toDtoModel(trainingPlanServicePort.find(number)));
    }

    @Override
    public ResponseEntity<List<TrainingPlanResponse>> getTrainingPlanByTrainer(
        String trainerLogin) {
        return TrainingPlansApiDelegate.super.getTrainingPlanByTrainer(trainerLogin);
    }

    @Override
    public ResponseEntity<List<TrainingPlanResponse>> getTrainingPlans() {
        return ResponseEntity.ok(trainingPlanServicePort.findAll().stream()
            .map(trainingPlanResponseMapper::toDtoModel)
            .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<TrainingPlanDetailsResponse> createTrainingPlan(
        TrainingPlanRequestPost trainingPlanRequestPost) {
        TrainingPlan trainingPlan = trainingPlanRequestPostMapper
            .toDomainModel(trainingPlanRequestPost);
        TrainingPlan saved = trainingPlanServicePort.save(trainingPlan);
        URI location = URI.create(String.format("/trainingPlans/%s", saved.getNumber()));
        return ResponseEntity.created(location)
            .body(trainingPlanDetailsResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<TrainingPlanDetailsResponse> updateTrainingPlan(String number,
        TrainingPlanRequestPut trainingPlanRequestPut) {
        TrainingPlan trainingPlan = trainingPlanRequestPutMapper
            .toDomainModel(trainingPlanRequestPut);
        TrainingPlan updated = trainingPlanServicePort.update(number, trainingPlan);
        return ResponseEntity.ok(trainingPlanDetailsResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<Void> deleteTrainingPlan(String number) {
        trainingPlanServicePort.delete(number);
        return ResponseEntity.ok().build();
    }


}

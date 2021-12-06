package pl.lodz.p.it.restapi.controllerImplementation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.core.port.primary.TrainingPlanServicePort;
import pl.lodz.p.it.restapi.controller.TrainingPlansApiDelegate;
import pl.lodz.p.it.restapi.dto.TrainingPlanRequestPost;
import pl.lodz.p.it.restapi.dto.TrainingPlanRequestPut;
import pl.lodz.p.it.restapi.dto.TrainingPlanResponse;
import pl.lodz.p.it.restapi.mapper.training.TrainingPlanRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.training.TrainingPlanRequestPutMapper;
import pl.lodz.p.it.restapi.mapper.training.TrainingPlanResponseMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TrainingPlanController implements TrainingPlansApiDelegate {

    private final TrainingPlanServicePort trainingPlanServicePort;

    private final TrainingPlanResponseMapper trainingPlanResponseMapper;

    private final TrainingPlanRequestPostMapper trainingPlanRequestPostMapper;

    private final TrainingPlanRequestPutMapper trainingPlanRequestPutMapper;

    @Override
    public ResponseEntity<TrainingPlanResponse> getTrainingPlan(String number) {
        return ResponseEntity.ok(trainingPlanResponseMapper.toDtoModel(trainingPlanServicePort.find(number)));
    }

    @Override
    public ResponseEntity<List<TrainingPlanResponse>> getTrainingPlanByTrainer(String trainerLogin) {
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
    public ResponseEntity<TrainingPlanResponse> createTrainingPlan(TrainingPlanRequestPost trainingPlanRequestPost) {
        TrainingPlan trainingPlan = trainingPlanRequestPostMapper.toDomainModel(trainingPlanRequestPost);
        TrainingPlan saved = trainingPlanServicePort.save(trainingPlan);
        return ResponseEntity.ok(trainingPlanResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<TrainingPlanResponse> updateTrainingPlan(String number, TrainingPlanRequestPut trainingPlanRequestPut) {
        TrainingPlan trainingPlan = trainingPlanRequestPutMapper.toDomainModel(trainingPlanRequestPut);
        TrainingPlan updated = trainingPlanServicePort.update(number, trainingPlan);
        return ResponseEntity.ok(trainingPlanResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<Void> deleteTrainingPlan(String number) {
        trainingPlanServicePort.delete(number);
        return ResponseEntity.ok().build();
    }


}

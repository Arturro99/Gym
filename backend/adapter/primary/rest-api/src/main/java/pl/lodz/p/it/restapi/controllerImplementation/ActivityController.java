package pl.lodz.p.it.restapi.controllerImplementation;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.port.primary.ActivityServicePort;
import pl.lodz.p.it.restapi.controller.ActivitiesApiDelegate;
import pl.lodz.p.it.restapi.dto.ActivityDetailsResponse;
import pl.lodz.p.it.restapi.dto.ActivityRequestPost;
import pl.lodz.p.it.restapi.dto.ActivityRequestPut;
import pl.lodz.p.it.restapi.dto.ActivityResponse;
import pl.lodz.p.it.restapi.mapper.activity.ActivityDetailsResponseMapper;
import pl.lodz.p.it.restapi.mapper.activity.ActivityRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.activity.ActivityRequestPutMapper;
import pl.lodz.p.it.restapi.mapper.activity.ActivityResponseMapper;

@RestController
@AllArgsConstructor
public class ActivityController implements ActivitiesApiDelegate {

    private final ActivityServicePort activityServicePort;

    private final ActivityResponseMapper activityResponseMapper;

    private final ActivityDetailsResponseMapper activityDetailsResponseMapper;

    private final ActivityRequestPostMapper activityRequestPostMapper;

    private final ActivityRequestPutMapper activityRequestPutMapper;

    @Override
    public ResponseEntity<ActivityDetailsResponse> getActivity(String number) {
        return ResponseEntity
            .ok(activityDetailsResponseMapper.toDtoModel(activityServicePort.find(number)));
    }

    @Override
    public ResponseEntity<List<ActivityResponse>> getActivities() {
        return ResponseEntity.ok(activityServicePort.findAll().stream()
            .map(activityResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ActivityDetailsResponse> createActivity(
        ActivityRequestPost activityRequestPost) {
        Activity activity = activityRequestPostMapper.toDomainModel(activityRequestPost);
        Activity saved = activityServicePort.save(activity);
        return ResponseEntity.ok(activityDetailsResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<ActivityResponse> updateActivity(String number,
        ActivityRequestPut activityRequestPut) {
        Activity activity = activityRequestPutMapper.toDomainModel(activityRequestPut);
        Activity updated = activityServicePort.update(number, activity);
        return ResponseEntity.ok(activityResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<Void> deleteActivity(String number) {
        activityServicePort.delete(number);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ActivityResponse>> getActivityByTrainer(String login) {
        return ResponseEntity.ok(activityServicePort.findByTrainer(login).stream()
            .map(activityResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }
}

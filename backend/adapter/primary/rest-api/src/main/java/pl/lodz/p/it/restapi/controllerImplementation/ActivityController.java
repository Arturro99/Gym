package pl.lodz.p.it.restapi.controllerImplementation;

import static lombok.AccessLevel.PRIVATE;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ActivityController implements ActivitiesApiDelegate {

    ActivityServicePort activityServicePort;

    ActivityResponseMapper activityResponseMapper;

    ActivityDetailsResponseMapper activityDetailsResponseMapper;

    ActivityRequestPostMapper activityRequestPostMapper;

    ActivityRequestPutMapper activityRequestPutMapper;

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
        URI location = URI.create(String.format("/activities/%s", saved.getNumber()));
        return ResponseEntity.created(location)
            .body(activityDetailsResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<ActivityResponse> updateActivity(String number,
        ActivityRequestPut activityRequestPut) {
        Activity activity = activityRequestPutMapper.toDomainModel(activityRequestPut);
        Activity updated = activityServicePort.update(number, activity);
        return ResponseEntity.ok(activityResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<Void> deactivateActivity(String number) {
        activityServicePort.deactivate(number);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ActivityResponse>> getActivityByTrainer(String login) {
        return ResponseEntity.ok(activityServicePort.findByTrainer(login).stream()
            .map(activityResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }
}

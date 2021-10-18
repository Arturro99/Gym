package pl.lodz.p.it.core.application.secondary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.ActivityMapper;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;
import pl.lodz.p.it.repositoryhibernate.repository.ActivityRepository;

/**
 * Service class responsible for operating on activity repository.
 */
@Component
@AllArgsConstructor
public class ActivityRepositoryService extends BaseRepositoryService<ActivityEntity, Activity> implements
    ActivityRepositoryPort {

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

//    @Override
//    public Optional<Activity> find(String key) {
//        return activityRepository.findByNumber(key)
//            .map(activityMapper::toActivity);
//    }
//
//    @Override
//    public List<Activity> findAll() {
//        return activityRepository.findAll().stream()
//            .map(activityMapper::toActivity)
//            .collect(Collectors.toList());
//    }
//
//    @Override
//    public Activity save(Activity activity) {
//        ActivityEntity activityEntity = activityMapper.toActivityEntity(activity);
//        ActivityEntity savedEntity = activityRepository.save(activityEntity);
//        return activityMapper.toActivity(savedEntity);
//    }
//
//    @Override
//    public Activity update(String key, Activity activity) {
//        ActivityEntity activityEntity = activityRepository.findByNumber(key).orElseThrow(
//            ActivityException::activityNotFoundException);
//        ActivityEntity updated = activityMapper
//            .toActivityEntity(activityEntity, activity);
//        ActivityEntity response = activityRepository.save(updated);
//        return activityMapper.toActivity(response);
//    }
//
//    @Override
//    public void delete(String key) {
//        ActivityEntity activityEntity = activityRepository.findByNumber(key)
//            .orElseThrow(ActivityException::activityNotFoundException);
//        activityRepository.delete(activityEntity);
//    }
}

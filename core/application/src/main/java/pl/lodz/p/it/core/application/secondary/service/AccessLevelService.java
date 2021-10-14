package pl.lodz.p.it.core.application.secondary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.AccessLevelMapper;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.port.secondary.AccessLevelRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;

/**
 * Service class responsible for operating on access level repository.
 */
@Component
@AllArgsConstructor
public class AccessLevelService extends BaseService<AccessLevelEntity, AccessLevel> implements
    AccessLevelRepositoryPort {

    private final AccessLevelRepository accessLevelRepository;

    private final AccessLevelMapper accessLevelMapper;

//    @Override
//    public Optional<AccessLevel> find(String key) {
//        return accessLevelRepository.findByLevel(key)
//            .map(accessLevelMapper::toAccessLevel);
//    }
//
//    @Override
//    public List<AccessLevel> findAll() {
//        return accessLevelRepository.findAll().stream()
//            .map(accessLevelMapper::toAccessLevel)
//            .collect(Collectors.toList());
//    }
//
//    @Override
//    public AccessLevel save(AccessLevel accessLevel) {
//        AccessLevelEntity accessLevelEntity = accessLevelMapper.toAccessLevelEntity(accessLevel);
//        AccessLevelEntity savedEntity = accessLevelRepository.save(accessLevelEntity);
//        return accessLevelMapper.toAccessLevel(savedEntity);
//    }
//
//    @Override
//    public AccessLevel update(String key, AccessLevel accessLevel) {
//        AccessLevelEntity accessLevelEntity = accessLevelRepository.findByLevel(key).orElseThrow(
//            AccessLevelException::accessLevelNotFoundException);
//        AccessLevelEntity updated = accessLevelMapper
//            .toAccessLevelEntity(accessLevelEntity, accessLevel);
//        AccessLevelEntity response = accessLevelRepository.save(updated);
//        return accessLevelMapper.toAccessLevel(response);
//    }
//
//    @Override
//    public void delete(String key) {
//        AccessLevelEntity accessLevelEntity = accessLevelRepository.findByLevel(key)
//            .orElseThrow(AccessLevelException::accessLevelNotFoundException);
//        accessLevelRepository.delete(accessLevelEntity);
//    }
}

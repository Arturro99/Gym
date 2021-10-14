package pl.lodz.p.it.core.application.secondary.mapper;

import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.BookingEntity;

/**
 * Interface responsible for mapping {@link Booking} objects and {@link BookingEntity}
 */

public interface BookingMapper extends BaseMapper<AccessLevelEntity, AccessLevel> {

}

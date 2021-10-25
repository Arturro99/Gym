package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.BookingMapper;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.BookingEntity;
import pl.lodz.p.it.repositoryhibernate.repository.BookingRepository;

/**
 * Service class responsible for operating on booking repository.
 */
@Component
public class BookingRepositoryService extends BaseRepositoryService<BookingEntity, Booking> implements
        BookingRepositoryPort {

    @Autowired
    public BookingRepositoryService(BookingRepository repository, BookingMapper mapper) {
        super(repository, mapper);
    }
}

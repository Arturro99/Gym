package pl.lodz.p.it.restapi.controllerImplementation;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.primary.BookingServicePort;
import pl.lodz.p.it.restapi.controller.BookingsApiDelegate;
import pl.lodz.p.it.restapi.dto.BookingDetailsResponse;
import pl.lodz.p.it.restapi.dto.BookingRequestPost;
import pl.lodz.p.it.restapi.dto.BookingResponse;
import pl.lodz.p.it.restapi.mapper.booking.BookingDetailsResponseMapper;
import pl.lodz.p.it.restapi.mapper.booking.BookingRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.booking.BookingResponseMapper;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookingController implements BookingsApiDelegate {

    BookingServicePort bookingServicePort;

    BookingResponseMapper bookingResponseMapper;

    BookingDetailsResponseMapper bookingDetailsResponseMapper;

    BookingRequestPostMapper bookingRequestPostMapper;

    @Override
    public ResponseEntity<BookingDetailsResponse> getBooking(String number) {
        return ResponseEntity
            .ok(bookingDetailsResponseMapper.toDtoModel(bookingServicePort.find(number)));
    }

    @Override
    public ResponseEntity<List<BookingResponse>> getBookings() {
        return ResponseEntity.ok(bookingServicePort.findAll().stream()
            .map(bookingResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<BookingDetailsResponse> createOwnBooking(
        BookingRequestPost bookingRequestPost) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Booking booking = bookingRequestPostMapper.toDomainModel(bookingRequestPost);
        booking.setAccount(login);
        Booking saved = bookingServicePort.save(booking);
        return ResponseEntity.ok(bookingDetailsResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<BookingDetailsResponse> createBooking(BookingRequestPost bookingRequestPost) {
        Booking booking = bookingRequestPostMapper.toDomainModel(bookingRequestPost);
        booking.setAccount(bookingRequestPost.getAccount());
        Booking saved = bookingServicePort.save(booking);
        return ResponseEntity.ok(bookingDetailsResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<BookingDetailsResponse> cancelBooking(String number) {
        Booking updated = bookingServicePort.cancelBooking(number);
        return ResponseEntity.ok(bookingDetailsResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<BookingDetailsResponse> completeBooking(String number) {
        Booking updated = bookingServicePort.completeBooking(number);
        return ResponseEntity.ok(bookingDetailsResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<BookingDetailsResponse> cancelOwnBooking(String number) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Booking updated = bookingServicePort.cancelBooking(number, login);
        return ResponseEntity.ok(bookingDetailsResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<BookingDetailsResponse> getOwnBooking(String number) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity
            .ok(bookingDetailsResponseMapper
                .toDtoModel(bookingServicePort.findByClientAndNumber(login, number)));
    }

    @Override
    public ResponseEntity<List<BookingResponse>> getOwnBookings() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(bookingServicePort.findByClient(login).stream()
            .map(bookingResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }
}

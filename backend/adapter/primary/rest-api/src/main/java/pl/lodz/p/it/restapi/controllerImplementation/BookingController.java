package pl.lodz.p.it.restapi.controllerImplementation;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class BookingController implements BookingsApiDelegate {

    private final BookingServicePort bookingServicePort;

    private final BookingResponseMapper bookingResponseMapper;

    private final BookingDetailsResponseMapper bookingDetailsResponseMapper;

    private final BookingRequestPostMapper bookingRequestPostMapper;

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
    public ResponseEntity<List<BookingResponse>> getBookingsByActivity(String number) {
        return ResponseEntity.ok(bookingServicePort.findByActivity(number).stream()
            .map(bookingResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<BookingResponse>> getBookingsByClient(String login) {
        return ResponseEntity.ok(bookingServicePort.findByClient(login).stream()
            .map(bookingResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<BookingDetailsResponse> createBooking(
        BookingRequestPost bookingRequestPost) {
        Booking booking = bookingRequestPostMapper.toDomainModel(bookingRequestPost);
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
}

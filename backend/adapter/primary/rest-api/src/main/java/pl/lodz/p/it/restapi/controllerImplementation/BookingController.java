package pl.lodz.p.it.restapi.controllerImplementation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.primary.BookingServicePort;
import pl.lodz.p.it.restapi.controller.BookingsApiDelegate;
import pl.lodz.p.it.restapi.dto.BookingRequestPost;
import pl.lodz.p.it.restapi.dto.BookingRequestPut;
import pl.lodz.p.it.restapi.dto.BookingResponse;
import pl.lodz.p.it.restapi.mapper.booking.BookingRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.booking.BookingRequestPutMapper;
import pl.lodz.p.it.restapi.mapper.booking.BookingResponseMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class BookingController implements BookingsApiDelegate {

    private final BookingServicePort bookingServicePort;

    private final BookingResponseMapper bookingResponseMapper;

    private final BookingRequestPostMapper bookingRequestPostMapper;

    private final BookingRequestPutMapper bookingRequestPutMapper;

    @Override
    public ResponseEntity<BookingResponse> getBooking(String number) {
        return ResponseEntity.ok(bookingResponseMapper.toDtoModel(bookingServicePort.find(number)));
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
    public ResponseEntity<BookingResponse> createBooking(BookingRequestPost bookingRequestPost) {
        Booking booking = bookingRequestPostMapper.toDomainModel(bookingRequestPost);
        Booking saved = bookingServicePort.save(booking);
        return ResponseEntity.ok(bookingResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<BookingResponse> updateBooking(String number, BookingRequestPut bookingRequestPut) {
        Booking booking = bookingRequestPutMapper.toDomainModel(bookingRequestPut);
        Booking updated = bookingServicePort.update(number, booking);
        return ResponseEntity.ok(bookingResponseMapper.toDtoModel(updated));
    }
}

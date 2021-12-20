package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.ConflictException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;

/**
 * Class responsible for creating exceptions associated with booking object.
 */
public class BookingException extends BaseException {

    private BookingException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static BookingException bookingNotFoundException() {
        return new BookingException(NotFoundException
            .notFound("Required booking not found!", ErrorKey.BOOKING_NOT_FOUND_ERROR));
    }

    public static BookingException bookingConflictException() {
        return new BookingException(
            ConflictException
                .conflict("Booking already exists and is active!", ErrorKey.BOOKING_CONFLICT_ERROR));
    }
}

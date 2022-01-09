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

    public static BookingException bookingActivityInactiveConflictException() {
        return new BookingException(
            ConflictException
                .conflict("Cannot book a place in an inactive activity!",
                    ErrorKey.BOOKING_CONFLICT_ACTIVITY_INACTIVE_ERROR));
    }

    public static BookingException possessedBookingConflictException() {
        return new BookingException(
            ConflictException
                .conflict("User already possesses the booking!", ErrorKey.BOOKING_CONFLICT_ERROR));
    }

    public static BookingException bookingCancellationDeadlineException() {
        return new BookingException(
            ConflictException
                .conflict("Booking can no longer be cancelled!",
                    ErrorKey.BOOKING_CONFLICT_CANCELLATION_ERROR));
    }

    public static BookingException bookingCompletionException() {
        return new BookingException(
            ConflictException
                .conflict("Cannot complete booking before the start of activity!",
                    ErrorKey.BOOKING_CONFLICT_COMPLETION_ERROR));
    }

    public static BookingException bookingClientTrainerConflictException() {
        return new BookingException(
            ConflictException
                .conflict("Client is also running this activity!",
                    ErrorKey.BOOKING_CONFLICT_CLIENT_TRAINER_ERROR));
    }
}

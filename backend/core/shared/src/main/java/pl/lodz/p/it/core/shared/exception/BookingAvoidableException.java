package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;

/**
 * Class responsible for creating exceptions associated with booking object. The exception is used
 * for indicating whether findByAccountAndActivity() method in BookingRepository returns a concrete
 * object. If not - a {@link BookingAvoidableException} is thrown, which is not considered as
 * rollback reason for TXs.
 */
public class BookingAvoidableException extends BaseException {

    private BookingAvoidableException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static BookingAvoidableException bookingNotFoundException() {
        return new BookingAvoidableException(NotFoundException
            .notFound("Required booking not found!", ErrorKey.BOOKING_NOT_FOUND_ERROR));
    }
}

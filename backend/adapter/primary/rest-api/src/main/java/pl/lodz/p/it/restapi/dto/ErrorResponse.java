package pl.lodz.p.it.restapi.dto;

import lombok.Value;

import java.time.LocalDateTime;

/**
 * Class responsible for keeping a dto model of application errors.
 */
@Value
public class ErrorResponse {

    LocalDateTime dateTime;
    SingleError error;

    private ErrorResponse(SingleError singleError) {
        this.dateTime = LocalDateTime.now();
        this.error = singleError;
    }

    /**
     * Method responsible for creating a dto with an application error represented by {@link SingleError}.
     *
     * @param errorKey The error's key
     * @param message  The error's description
     * @return New object of type {@link ErrorResponse}
     */
    public static ErrorResponse error(String errorKey, String message) {
        return new ErrorResponse(new SingleError(errorKey, message));
    }
}

package pl.lodz.p.it.restapi.dto;

import lombok.Value;

/**
 * Class responsible for keeping a single object created by error occurred in application.
 */
@Value
public class SingleError {

    String errorKey;
    String message;
}

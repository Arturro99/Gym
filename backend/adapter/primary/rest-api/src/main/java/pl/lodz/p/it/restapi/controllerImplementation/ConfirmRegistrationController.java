package pl.lodz.p.it.restapi.controllerImplementation;

import static java.time.temporal.ChronoUnit.MINUTES;
import static lombok.AccessLevel.PRIVATE;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.core.port.primary.DisposableUrlServicePort;
import pl.lodz.p.it.core.shared.exception.DisposableUrlException;
import pl.lodz.p.it.restapi.controller.ConfirmRegistrationApiDelegate;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ConfirmRegistrationController implements ConfirmRegistrationApiDelegate {

    DisposableUrlServicePort disposableUrlServicePort;

    @Override
    public ResponseEntity<Void> confirmRegistration(String token) {
        disposableUrlServicePort.confirmRegistration(token);
        return ResponseEntity.ok().build();
    }
}

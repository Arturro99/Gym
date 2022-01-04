package pl.lodz.p.it.restapi.controllerImplementation;

import static lombok.AccessLevel.PRIVATE;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.primary.AccessLevelServicePort;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.core.shared.SecurityConstants;
import pl.lodz.p.it.core.shared.exception.SecurityException;
import pl.lodz.p.it.restapi.controller.AuthenticateApiDelegate;
import pl.lodz.p.it.restapi.dto.CredentialsRequest;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthController implements AuthenticateApiDelegate {

    AuthenticationManager authenticationManager;

    AccessLevelServicePort accessLevelServicePort;

    AccountServicePort accountServicePort;

    @CrossOrigin
    @Override
    public ResponseEntity<String> authenticate(CredentialsRequest credentialsRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentialsRequest.getLogin(),
                    credentialsRequest.getPassword()));
            Account account = accountServicePort.find(credentialsRequest.getLogin());
            account.setBadLoginsCounter(0);
            accountServicePort.update(credentialsRequest.getLogin(), account);
        } catch (LockedException ex) {
            throw SecurityException.accountInactiveException();
        } catch (BadCredentialsException ex) {
            Account account = accountServicePort.find(credentialsRequest.getLogin());
            account.setBadLoginsCounter(account.getBadLoginsCounter() + 1);
            if (account.getBadLoginsCounter() == 3) {
                account.setActive(false);
            }
            accountServicePort.update(credentialsRequest.getLogin(), account);
            throw ex;
        }

        String token = generateToken(credentialsRequest,
            accessLevelServicePort
                .findByAccount(accountServicePort.find(credentialsRequest.getLogin())).stream()
                .map(AccessLevel::getLevel)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(token);
    }

    private String generateToken(CredentialsRequest credentialsRequest, List<String> roles) {
        return Jwts.builder()
            .claim(SecurityConstants.AUTH, String.join(SecurityConstants.ROLE_SEPARATOR, roles))
            .setSubject(credentialsRequest.getLogin())
            .setIssuer(SecurityConstants.ISSUER)
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + SecurityConstants.JWT_TIMEOUT))
            .signWith(SignatureAlgorithm.HS256, SecurityConstants.AUTH_SECRET)
            .compact();
    }


}

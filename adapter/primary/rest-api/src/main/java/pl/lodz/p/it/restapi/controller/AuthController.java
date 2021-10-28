package pl.lodz.p.it.restapi.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.port.primary.AccessLevelServicePort;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.core.shared.SecurityConstants;
import pl.lodz.p.it.restapi.dto.CredentialsRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthController implements AuthenticateApiDelegate {

    private final AuthenticationManager authenticationManager;

    private final AccessLevelServicePort accessLevelServicePort;

    private final AccountServicePort accountServicePort;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AccessLevelServicePort accessLevelServicePort,
                          AccountServicePort accountServicePort) {
        this.authenticationManager = authenticationManager;
        this.accessLevelServicePort = accessLevelServicePort;
        this.accountServicePort = accountServicePort;
    }

    @CrossOrigin
    @Override
    public ResponseEntity<String> authenticate(CredentialsRequest credentialsRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentialsRequest.getLogin(), credentialsRequest.getPassword()));

        String token = generateToken(credentialsRequest,
                accessLevelServicePort.findByAccount(accountServicePort.find(credentialsRequest.getLogin())).stream()
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

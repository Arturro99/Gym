package pl.lodz.p.it.gymbackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.shared.SecurityConstants;

import java.util.Date;
import java.util.function.Function;
import pl.lodz.p.it.core.shared.exception.SecurityException;

@Component
public class JWTUtils {

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationTime(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        if (!userDetails.isAccountNonLocked()) {
            throw SecurityException.accountInactiveException();
        }
        String login = extractUsername(token);
        return (login.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.AUTH_SECRET).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpirationTime(token).before(new Date());
    }
}

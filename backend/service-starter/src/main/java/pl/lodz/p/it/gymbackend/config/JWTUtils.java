package pl.lodz.p.it.gymbackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.shared.SecurityConstants;
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
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.AUTH_SECRET)
            .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpirationTime(token).before(new Date());
    }

    public String refreshToken(String account, List<GrantedAuthority> authorities) {
        List<String> roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return Jwts.builder()
            .claim(SecurityConstants.AUTH, String.join(SecurityConstants.ROLE_SEPARATOR, roles))
            .setIssuer(SecurityConstants.ISSUER)
            .setSubject(account)
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + SecurityConstants.JWT_TIMEOUT))
            .signWith(SignatureAlgorithm.HS256, SecurityConstants.AUTH_SECRET)
            .compact();
    }
}

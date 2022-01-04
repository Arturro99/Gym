package pl.lodz.p.it.gymbackend.config;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.lodz.p.it.core.shared.SecurityConstants;
import pl.lodz.p.it.core.shared.exception.SecurityException;

@Component
@AllArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        response.addHeader("Access-Control-Expose-Headers", "Refresh-Token");

        String jwt = null;
        String username = null;
        String newToken;

        try {
            if (authorizationHeader != null && authorizationHeader
                .startsWith(SecurityConstants.BEARER)) {
                jwt = authorizationHeader.substring(SecurityConstants.BEARER.length()).trim();
                username = jwtUtils.extractUsername(jwt);
            }

            if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtils.validateToken(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);
                    newToken = jwtUtils.refreshToken(userDetails.getUsername(),
                        (List<GrantedAuthority>) userDetails.getAuthorities());
                    response.addHeader("Refresh-Token", newToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            throw SecurityException.jwtExpiredException();
        }
    }
}

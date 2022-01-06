package pl.lodz.p.it.gymbackend.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.List;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.lodz.p.it.core.shared.constant.Level;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String ADMIN = Level.ADMIN.name();
    private final static String CLIENT = Level.CLIENT.name();
    private final static String TRAINER = Level.TRAINER.name();
    private final JWTRequestFilter jwtRequestFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            //ACCOUNTS
            .antMatchers(GET, "/accounts").hasAuthority(ADMIN)
            .antMatchers(GET, "/accounts/own").authenticated()
            .antMatchers(GET, "/accounts/own/*").authenticated()
            .antMatchers(PUT, "/accounts/own").authenticated()
            .antMatchers(DELETE, "/accounts/own/*").authenticated()
            .antMatchers(POST, "/accounts/own/*").authenticated()
            .antMatchers(GET, "/accounts/**").hasAuthority(ADMIN)
            .antMatchers(PUT, "/accounts/**").hasAuthority(ADMIN)
            //TRAINING PLANS
            .antMatchers(GET, "/trainingPlans/**").hasAnyAuthority(CLIENT, TRAINER)
            .antMatchers(POST, "/trainingPlans").hasAuthority(TRAINER)
            .antMatchers(PUT, "/trainingPlans/*").hasAuthority(TRAINER)
            .antMatchers(DELETE, "/trainingPlans/*").hasAuthority(TRAINER)
            //DIETS
            .antMatchers(GET, "/diets/**").hasAnyAuthority(CLIENT, TRAINER)
            .antMatchers(POST, "/diets").hasAuthority(TRAINER)
            .antMatchers(PUT, "/diets/*").hasAuthority(TRAINER)
            .antMatchers(DELETE, "/diets/*").hasAuthority(TRAINER)
            //ACTIVITIES
            .antMatchers(GET, "/activities/**").hasAnyAuthority(CLIENT, TRAINER)
            .antMatchers(POST, "/activities").hasAuthority(TRAINER)
            .antMatchers(PUT, "/activities/*").hasAuthority(TRAINER)
            .antMatchers(DELETE, "/activities/*").hasAuthority(TRAINER)
            //BOOKINGS
            .antMatchers(GET, "/bookings/own").hasAuthority(CLIENT)
            .antMatchers(GET, "/bookings/own/*").hasAuthority(CLIENT)
            .antMatchers(POST, "/bookings").hasAuthority(CLIENT)
            .antMatchers(PUT, "/bookings/own/**").hasAuthority(CLIENT)
            .antMatchers(GET, "/bookings/**").hasAuthority(TRAINER)
            .antMatchers(PUT, "/bookings/**").hasAuthority(TRAINER)
            //ACCESS LEVELS
            .antMatchers(POST, "/accessLevels").hasAuthority(ADMIN)
            .antMatchers(GET, "/accessLevels").hasAuthority(ADMIN)
            .antMatchers(GET, "/accessLevels/*").hasAuthority(ADMIN)
            .antMatchers(DELETE, "/accessLevels/**").hasAuthority(ADMIN)
            //DIET TYPES
            .antMatchers(GET, "/dietTypes").authenticated()
            .antMatchers(GET, "/trainingTypes").authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, LogoutFilter.class);
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

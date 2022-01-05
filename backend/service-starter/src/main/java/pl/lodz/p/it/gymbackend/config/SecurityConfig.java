package pl.lodz.p.it.gymbackend.config;

import java.util.List;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
            .antMatchers(HttpMethod.GET, "/accounts").hasAuthority(ADMIN)
            .antMatchers(HttpMethod.GET, "/accounts/own").authenticated()
            .antMatchers(HttpMethod.GET, "/accounts/own/*").authenticated()
            .antMatchers(HttpMethod.PUT, "/accounts/own").authenticated()
            .antMatchers(HttpMethod.DELETE, "/accounts/own/*").authenticated()
            .antMatchers(HttpMethod.POST, "/accounts/own/*").authenticated()
            .antMatchers(HttpMethod.GET, "/accounts/**").hasAuthority(ADMIN)
            .antMatchers(HttpMethod.PUT, "/accounts/**").hasAuthority(ADMIN)
            //TRAINING PLANS
            .antMatchers(HttpMethod.GET, "/trainingPlans/**").hasAnyAuthority(CLIENT, TRAINER)
            .antMatchers(HttpMethod.POST, "/trainingPlans").hasAuthority(TRAINER)
            .antMatchers(HttpMethod.PUT, "/trainingPlans/*").hasAuthority(TRAINER)
            .antMatchers(HttpMethod.DELETE, "/trainingPlans/*").hasAuthority(TRAINER)
            //DIETS
            .antMatchers(HttpMethod.GET, "/diets/**").hasAnyAuthority(CLIENT, TRAINER)
            .antMatchers(HttpMethod.POST, "/diets").hasAuthority(TRAINER)
            .antMatchers(HttpMethod.PUT, "/diets/*").hasAuthority(TRAINER)
            .antMatchers(HttpMethod.DELETE, "/diets/*").hasAuthority(TRAINER)
            //ACTIVITIES
            .antMatchers(HttpMethod.GET, "/activities/**").hasAnyAuthority(CLIENT, TRAINER)
            .antMatchers(HttpMethod.POST, "/activities").hasAuthority(TRAINER)
            .antMatchers(HttpMethod.PUT, "/activities/*").hasAuthority(TRAINER)
            .antMatchers(HttpMethod.DELETE, "/activities/*").hasAuthority(TRAINER)
            //BOOKINGS
            .antMatchers(HttpMethod.GET, "/bookings/own").hasAuthority(CLIENT)
            .antMatchers(HttpMethod.GET, "/bookings/own/*").hasAuthority(CLIENT)
            .antMatchers(HttpMethod.POST, "/bookings").hasAuthority(CLIENT)
            .antMatchers(HttpMethod.PUT, "/bookings/own/**").hasAuthority(CLIENT)
            .antMatchers(HttpMethod.GET, "/bookings/**").hasAuthority(TRAINER)
            .antMatchers(HttpMethod.PUT, "/bookings/**").hasAuthority(TRAINER)
            //ACCESS LEVELS
            .antMatchers(HttpMethod.POST, "/accessLevels").hasAuthority(ADMIN)
            .antMatchers(HttpMethod.GET, "/accessLevels").hasAuthority(ADMIN)
            .antMatchers(HttpMethod.GET, "/accessLevels/*").hasAuthority(ADMIN)
            .antMatchers(HttpMethod.DELETE, "/accessLevels/**").hasAuthority(ADMIN)
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

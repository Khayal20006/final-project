package com.example.finalproject.config;

import com.example.finalproject.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/actuator/health").permitAll()

                        .requestMatchers("/hotel/getAllHotels", "/hotel/getHotelByID/**").authenticated()

                        .requestMatchers("/hotel/admin/**").hasRole("ADMIN")

                        .requestMatchers("/hotel/room/allAvailable", "/hotel/room/all", "/hotel/room/{id}").authenticated()
                        .requestMatchers("/hotel/room/filter/**", "/hotel/room/availableByDate").authenticated()

                        .requestMatchers("/hotel/room/admin/**", "/hotel/room/add", "/hotel/room/update/**", "/hotel/room/delete/**").hasRole("ADMIN")

                        .requestMatchers("/hotel/reservations").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/hotel/reservations/user/**", "/hotel/reservations/byUser/**").authenticated()
                        .requestMatchers("/hotel/reservations/status/**").hasRole("ADMIN")
                        .requestMatchers("/hotel/reservations/{id}/status").hasRole("ADMIN")
                        .requestMatchers("/hotel/reservations/{id}/cancel").authenticated()

                        .requestMatchers("/hotel/payments/**").authenticated()

                        .requestMatchers("/hotel/users/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

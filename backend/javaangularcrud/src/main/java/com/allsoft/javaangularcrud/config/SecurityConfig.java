package com.allsoft.javaangularcrud.config;

import com.allsoft.javaangularcrud.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
  private static final String RUTAPRODUCTO = "/api/productos/**";
  private static final String VENDEDOR = "VENDEDOR";
  private static final String ADMIN = "ADMIN";
  private static final String CLIENTE = "CLIENTE";
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    AccessDeniedHandler accessDeniedHandler = (request, response, accessDeniedException) -> {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.getWriter().write("No esta autorizado para realizar esta peticion.");
    };
    http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/authUser/**","/api/authSeller/**").permitAll()
                    .requestMatchers("/api/cart/**").authenticated()
                    .requestMatchers("/api/user/**").authenticated()
                    .requestMatchers("/api/orders/**").hasAnyRole(VENDEDOR,ADMIN)
                    .requestMatchers("/api/orders/create").authenticated()
                    .requestMatchers(HttpMethod.GET, RUTAPRODUCTO).hasAnyRole(VENDEDOR, ADMIN, CLIENTE)
                    .requestMatchers(HttpMethod.POST, RUTAPRODUCTO).hasAnyRole(VENDEDOR, ADMIN, CLIENTE)
                    .requestMatchers(HttpMethod.PUT, RUTAPRODUCTO).hasAnyRole(VENDEDOR, ADMIN)
                    .requestMatchers(HttpMethod.DELETE, RUTAPRODUCTO).hasAnyRole(VENDEDOR, ADMIN)
                    .anyRequest().authenticated()
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                    .accessDeniedHandler(accessDeniedHandler)
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}

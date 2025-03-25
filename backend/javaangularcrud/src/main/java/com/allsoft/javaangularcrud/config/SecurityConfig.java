package com.allsoft.javaangularcrud.config;

import com.allsoft.javaangularcrud.entity.RoleName;
import com.allsoft.javaangularcrud.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
  private static final String RUTAPRODUCTO = "/api/productos/**";
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
    http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/authUser/**","/api/authSeller/**").permitAll()
                    .requestMatchers("/api/cart/**").permitAll()
                    .requestMatchers("/api/user/**").authenticated()
                    .requestMatchers(HttpMethod.GET, RUTAPRODUCTO).hasAnyRole(RoleName.VENDEDOR.name(), RoleName.ADMIN.name(), RoleName.CLIENTE.name())
                    .requestMatchers(HttpMethod.POST, RUTAPRODUCTO).hasAnyRole(RoleName.VENDEDOR.name(), RoleName.ADMIN.name(), RoleName.CLIENTE.name())
                    .requestMatchers(HttpMethod.PUT, RUTAPRODUCTO).hasAnyRole(RoleName.VENDEDOR.name(), RoleName.ADMIN.name())
                    .requestMatchers(HttpMethod.DELETE, RUTAPRODUCTO).hasAnyRole(RoleName.VENDEDOR.name(), RoleName.ADMIN.name())
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}

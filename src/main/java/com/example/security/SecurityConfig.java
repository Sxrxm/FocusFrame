package com.example.security;

import com.example.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    // Constructor con @Lazy en UserDetailsServiceImpl para evitar dependencia circular si es necesario
    public SecurityConfig(@Lazy UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Definir AuthenticationManager como un Bean
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService) // Usa tu implementación personalizada de UserDetailsService
                .passwordEncoder(passwordEncoder()); // Usamos un PasswordEncoder como BCryptPasswordEncoder

        return authenticationManagerBuilder.build();
    }

    // Bean para el PasswordEncoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración de la seguridad para manejar las rutas y autorizaciones
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz ->
                        authz
                                .requestMatchers("/auth/login", "/auth/register").permitAll()  // Permite el acceso sin autenticación a login y register
                                .anyRequest().authenticated()  // Resto de las rutas requieren autenticación
                );
        return http.build();
    }

}

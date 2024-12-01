package com.example.security.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, JwtTokenManager jwtTokenManager, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(String email, String password) {
        try {
            User user = userRepository.findByEmail(email);

            if (user == null) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Contraseña incorrecta");
            }

            return jwtTokenManager.generateToken(user);

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            logger.error("Error de autenticación: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al intentar autenticar al usuario: {}", e.getMessage(), e);
            throw new RuntimeException("Error en el proceso de autenticación", e);
        }
    }
}

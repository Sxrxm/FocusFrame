package com.example.security.service;


import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;

    @Lazy
    private final  BCryptPasswordEncoder passwordEncoder; // Uso de @Lazy aquí

    public String authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Contraseña o nombre de usuario incorrectos");
        }
        return jwtTokenManager.generateToken(user);
    }
}



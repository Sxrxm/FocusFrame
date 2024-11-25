package com.example.service;

import com.example.repository.UserRepository;
import com.example.security.dto.RegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

	private final UserRepository userRepository;

	public UserValidationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Método para validar el usuario durante el registro
	public void validateUser(RegistrationRequest registrationRequest) {
		// Validar que el correo electrónico no esté registrado previamente
		if (userRepository.findByEmail(registrationRequest.getEmail()) != null) {
			throw new IllegalArgumentException("El correo electrónico ya está registrado");
		}

		// Puedes agregar más validaciones, como verificar la fortaleza de la contraseña
		if (registrationRequest.getPassword().length() < 6) {
			throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
		}
	}
}

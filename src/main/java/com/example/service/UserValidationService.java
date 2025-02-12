package com.example.service;

import com.example.repository.UserRepository;
import com.example.security.dto.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

	@Autowired
	private UserRepository userRepository;

	public UserValidationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void validateUser(RegistrationRequest registrationRequest) {
		if (userRepository.findByEmail(registrationRequest.getEmail()) != null) {
			throw new IllegalArgumentException("El correo electrónico ya está registrado");
		}
		if (userRepository.findByUsername(registrationRequest.getUsername()) != null) {
			throw new IllegalArgumentException("Este nombre de usuario ya se encuentra en uso.");
		}

		if (registrationRequest.getPassword().length() < 12) {
			throw new IllegalArgumentException("La contraseña debe tener al menos 12 caracteres");
		}

		if (registrationRequest.getEmail().length() < 8) {
			throw new IllegalArgumentException("Ingrese un correo valido");
		}
	}
}

package com.example.security.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.dto.AuthenticatedUserDto;
import com.example.security.dto.RegistrationRequest;
import com.example.security.dto.RegistrationResponse;
import com.example.security.mapper.UserMapper;
import com.example.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private static final String REGISTRATION_SUCCESSFUL = "registration_successful";  // Clave del mensaje

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserValidationService userValidationService;
	private final MessageSource messageSource;  // Inyectamos MessageSource

	@Override
	public RegistrationResponse registration(RegistrationRequest registrationRequest) {

		// Validar el usuario usando la validación
		userValidationService.validateUser(registrationRequest);

		// Crear el usuario a partir de la solicitud de registro
		final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		// Usamos MessageSource para obtener el mensaje de éxito de registro
		final String email = registrationRequest.getEmail();  // Utilizamos el correo electrónico como parámetro
		String registrationSuccessMessage = messageSource.getMessage(
				REGISTRATION_SUCCESSFUL, new Object[]{email}, Locale.getDefault());

		log.info("Usuario registrado exitosamente: {}", user.getEmail());

		return new RegistrationResponse("Usuario registrado exitosamente.");
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public AuthenticatedUserDto findAuthenticatedUserByEmail(String email) {
		final User user = findByEmail(email);
		if (user == null) {
			return null;
		}
		return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
	}


}

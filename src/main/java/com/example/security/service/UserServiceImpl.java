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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


	private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserValidationService userValidationService;
	private final MessageSource messageSource;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserValidationService userValidationService, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userValidationService = userValidationService;
        this.messageSource = messageSource;
    }

    @Override
	public RegistrationResponse registration(RegistrationRequest registrationRequest) {

		userValidationService.validateUser(registrationRequest);

		final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		final String email = registrationRequest.getEmail();
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

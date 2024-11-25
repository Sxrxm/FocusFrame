package com.example.security.jwt;

import com.example.model.User;
import com.example.security.dto.AuthenticatedUserDto;
import com.example.security.dto.LoginRequest;
import com.example.security.dto.LoginResponse;
import com.example.security.mapper.UserMapper; // Asegúrate de que este Mapper esté correctamente definido
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

	private final UserService userService;
	private final JwtTokenManager jwtTokenManager;
	private final AuthenticationManager authenticationManager;

	public LoginResponse getLoginResponse(LoginRequest loginRequest) {

		final String email = loginRequest.getEmail();
		final String password = loginRequest.getPassword();

		final UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(email, password);

		authenticationManager.authenticate(authenticationToken);

		final AuthenticatedUserDto authenticatedUserDto = userService.findAuthenticatedUserByEmail(email);

		final User user = UserMapper.INSTANCE.convertToUser(authenticatedUserDto);

		final String token = jwtTokenManager.generateToken(user);

		log.info("{} has successfully logged in!", user.getEmail());

		return new LoginResponse(token);
	}
}

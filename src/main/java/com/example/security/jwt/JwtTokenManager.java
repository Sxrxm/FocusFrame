package com.example.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.model.User;
import com.example.model.UserRole;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenManager {

	private final JwtProperties jwtProperties;

	public JwtTokenManager(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	public String generateToken(User user) {
		String username = user.getUsername();
		UserRole userRole = user.getUserRole();

		return JWT.create()
				.withSubject(username)
				.withIssuer(jwtProperties.getIssuer())
				.withClaim("role", userRole.name())
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMinute() * 60 * 1000))
				.sign(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes()));
	}

	public String getUsernameFromToken(String token) {
		DecodedJWT decodedJWT = getDecodedJWT(token);
		return decodedJWT.getSubject();
	}

	public boolean validateToken(String token, String authenticatedUsername) {
		String usernameFromToken = getUsernameFromToken(token);
		boolean equalsUsername = usernameFromToken.equals(authenticatedUsername);
		boolean tokenExpired = isTokenExpired(token);
		return equalsUsername && !tokenExpired;
	}

	private boolean isTokenExpired(String token) {
		Date expirationDateFromToken = getExpirationDateFromToken(token);
		return expirationDateFromToken.before(new Date());
	}

	private Date getExpirationDateFromToken(String token) {
		DecodedJWT decodedJWT = getDecodedJWT(token);
		return decodedJWT.getExpiresAt();
	}

	private DecodedJWT getDecodedJWT(String token) {
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes())).build();
		return jwtVerifier.verify(token);
	}
}

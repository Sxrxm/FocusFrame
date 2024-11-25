package com.example.security.dto;

public class LoginResponse {

	private String token;

	public LoginResponse() {
	}

	// Constructor con argumento
	public LoginResponse(String token) {
		this.token = token;
	}

	// Getter y Setter para el token
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

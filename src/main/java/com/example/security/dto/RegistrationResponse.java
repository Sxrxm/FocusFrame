package com.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter

// Clase RegistrationResponse
public class RegistrationResponse {
	private String message;

	// Constructor sin parámetros (por defecto)
	public RegistrationResponse() {}

	// Constructor que acepta un String
	public RegistrationResponse(String message) {
		this.message = message;
	}

	// Getter y Setter
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}


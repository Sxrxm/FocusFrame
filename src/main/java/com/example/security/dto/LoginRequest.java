package com.example.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

	@Email(message = "Correo electrónico no válido")
	@NotBlank(message = "El correo electrónico no puede estar vacío")
	private String email;

	@NotBlank(message = "La contraseña no puede estar vacía")
	private String password;

}

package com.example.security.dto;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class RegistroPacienteRequest {

    @NotNull(message = "El teléfono es obligatorio.")
    private Long telefono;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El documento es obligatorio.")
    private Integer documento;

    @NotNull(message = "El email es obligatorio.")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }
}

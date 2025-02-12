package com.example.service;

import com.example.model.Paciente;
import com.example.model.User;
import com.example.model.UserRole;
import com.example.repository.PacienteRepository;
import com.example.repository.UserRepository;
import com.example.security.dto.RegistroPacienteRequest;
import com.example.security.jwt.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroPacienteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EmailService emailService;


    @Autowired
    private JwtTokenManager jwtTokenManager;

    public Paciente registrarPaciente(RegistroPacienteRequest registroPacienteRequest) {

        if (userRepository.findByEmail(registroPacienteRequest.getEmail()) != null) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }


        if (pacienteRepository.findByDocumento(registroPacienteRequest.getDocumento()) != null) {
            throw new IllegalArgumentException("Este número de documento ya está registrado");
        }


        User usuario = new User();
        usuario.setUserRole(UserRole.PACIENTE);
        usuario.setEmail(registroPacienteRequest.getEmail());
        usuario = userRepository.save(usuario);

        Paciente paciente = new Paciente();
        paciente.setTelefono(registroPacienteRequest.getTelefono());
        paciente.setDocumento(registroPacienteRequest.getDocumento());
        paciente.setFechaNacimiento(registroPacienteRequest.getFechaNacimiento());
        paciente.setEmail(registroPacienteRequest.getEmail());
        paciente.setUserRole(UserRole.PACIENTE);
        paciente.setUser(usuario);

        paciente = pacienteRepository.save(paciente);

        String token = jwtTokenManager.generateToken(usuario);

        String enlace = "http://localhost:8080/paciente/completal-perfil/" + paciente.getIdPaciente() + "?token=" + token;
        emailService.enviarCorreoConEnlace(paciente.getEmail(), enlace);

        return paciente;
    }
}

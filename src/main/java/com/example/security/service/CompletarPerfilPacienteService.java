package com.example.security.service;

import com.example.model.Paciente;
import com.example.model.User;
import com.example.repository.PacienteRepository;
import com.example.repository.UserRepository;
import com.example.security.dto.CompletarPerfilPacienteRequest;
import com.example.security.dto.PacienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CompletarPerfilPacienteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public PacienteResponse completarPerfil(Long pacienteId, CompletarPerfilPacienteRequest request) {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        User usuario = paciente.getUser();
        if (usuario == null) {
            throw new RuntimeException("El paciente no tiene un usuario asociado.");
        }



        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        paciente.setPerfilCompletado(true);
        userRepository.save(usuario);

        PacienteResponse response = new PacienteResponse();
        response.setIdPaciente(paciente.getIdPaciente());
        response.setUsername(usuario.getUsername());
        response.setEmail(usuario.getEmail());
        response.setTelefono(paciente.getTelefono());
        response.setFechaNacimiento(paciente.getFechaNacimiento());
        response.setDocumento(paciente.getDocumento());
        response.setEstado(paciente.getEstado());

        return response;
    }
}

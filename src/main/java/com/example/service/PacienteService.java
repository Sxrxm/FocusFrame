package com.example.service;


import com.example.model.Paciente;
import com.example.repository.PacienteRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private UserRepository userRepository;


    public Paciente buscarPacientePorId(Long id) {
        return pacienteRepository.findById(id).get();
    }

    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente actualizarPaciente(Long id,Paciente paciente) {
        if (pacienteRepository.existsById(id)) {
            paciente.setIdPaciente(id);
            return pacienteRepository.save(paciente);
        }else {
            throw new RuntimeException("Paciente no encontrado.");
        }
    }

    public Paciente desactivarPaciente(Long pacienteId) {
        Optional<Paciente> pacienteExistente = pacienteRepository.findById(pacienteId);

        if(pacienteExistente.isPresent()) {
            Paciente paciente = pacienteExistente.get();
            paciente.setEstado(false);
            return pacienteRepository.save(paciente);
        } else {
            throw new RuntimeException("Paciente no encontrado");
        }
    }
}

package com.example.service;

import com.example.model.Funcionario;
import com.example.model.Sesion;
import com.example.repository.FuncionarioRepository;
import com.example.repository.SesionRepository;
import com.example.security.dto.SesionRequest;
import com.example.security.dto.SesionResponse;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SesionService {

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Sesion> getAllSesiones() {
        return sesionRepository.findAll();
    }


    public Optional<Sesion> getSesionById(Long id) {
        return sesionRepository.findById(id);
    }

    public SesionResponse registrarSesion(SesionRequest sesionRequest) {

        Funcionario funcionario = funcionarioRepository.findById(sesionRequest.getIdFuncionario()).orElseThrow(() -> new RuntimeException("Funcionario no encontrado"));
        Sesion sesion = new Sesion();
        sesion.setFuncionario(funcionario);
        sesion.setNombre(sesionRequest.getNombre());
        sesion.setFechaSesion(sesionRequest.getFechaSesion());
        sesion.setHora(sesionRequest.getHora());
        sesion.setMonto(sesionRequest.getMonto());
        sesion.setMetodoPago(sesionRequest.getMetodoPago());
        sesion.setEstado(Sesion.EstadoSesion.PENDIENTE);



        sesion = sesionRepository.save(sesion);

        return new SesionResponse(
                sesion.getId(),
                sesion.getNombre(),
                sesion.getFuncionario().getIdFuncionario(),
                sesion.getPaciente().getIdPaciente(),
                sesion.getFechaSesion(),
                sesion.getHora(),
                sesion.getMonto(),
                sesion.getMetodoPago(),
                sesion.getEstado()
        );
    }


    // Actualizar
    public Sesion updateSesion(Long id, Sesion sesionDetails) {
        return sesionRepository.findById(id).map(sesion -> {
            sesion.setFechaSesion(sesionDetails.getFechaSesion());
            sesion.setEstado(sesionDetails.getEstado());
            return sesionRepository.save(sesion);
        }).orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
    }

    // Eliminar una sesión por ID
    public void deleteSesion(Long id) {
        sesionRepository.deleteById(id);
    }
    // Obtener sesiones por estado
    public List<Sesion> getSesionesByEstado(String estado) {
        return sesionRepository.findByEstado(estado);
    }
}

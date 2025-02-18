package com.example.controller;

import com.example.model.Paciente;
import com.example.repository.PacienteRepository;
import com.example.security.dto.CompletarPerfilPacienteRequest;
import com.example.security.dto.PacienteResponse;
import com.example.security.dto.RegistroPacienteRequest;
import com.example.security.jwt.JwtTokenManager;
import com.example.security.service.CompletarPerfilPacienteService;
import com.example.service.PacienteService;
import com.example.service.RegistroPacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/paciente")
@CrossOrigin(origins = "http://localhost:3000")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CompletarPerfilPacienteService completarPerfilPacienteService;

    @Autowired
    private RegistroPacienteService registroPacienteService;


    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;




    @PostMapping("/registrar")
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody RegistroPacienteRequest request) {
        Paciente paciente = registroPacienteService.registrarPaciente(request);
        return new ResponseEntity<>(paciente, HttpStatus.CREATED);
    }



    @PostMapping("/completar-perfil/{pacienteId}")
    public ResponseEntity<String> completarPerfil(@PathVariable Long pacienteId,@RequestParam String token, @RequestParam String username, @RequestParam String password) {

        Paciente paciente = registroPacienteService.verificarToken(pacienteId, token);

        if (paciente != null) {
            paciente.setEstado(true);
            paciente.setPerfilCompletado(true);
            paciente.getUser().setUsername(username);
            paciente.getUser().setPassword(passwordEncoder.encode(password));
            pacienteRepository.save(paciente);
            return new ResponseEntity<>("Perfil completado con éxito.", HttpStatus.OK);

        }
        return new ResponseEntity<>("Token inválido o paciente no encontrado.", HttpStatus.OK);

    }

    @PostMapping("/desactivar/{pacienteId}")
    public ResponseEntity<Paciente> desactivarPaciente(@PathVariable Long pacienteId) {
       try {
           Paciente pacienteDessactivado = pacienteService.desactivarPaciente(pacienteId);
           return new ResponseEntity<>(pacienteDessactivado, HttpStatus.OK);
       }catch (RuntimeException e){
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("/pacientes")
    public List<Paciente> getAllPacientes() {
        return pacienteService.getAllPacientes();
    }

    @PutMapping("/actualizarPaciente")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Long id,@RequestBody Paciente paciente) {
        try {
            Paciente pacienteActualizado = pacienteService.actualizarPaciente(id, paciente);
            return ResponseEntity.ok(pacienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}


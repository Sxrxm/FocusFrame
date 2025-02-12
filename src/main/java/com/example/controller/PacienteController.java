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

    @GetMapping("/completar-perfil/{pacienteId}")
    public String mostrarFormulario(@PathVariable String email, @RequestParam String token) {
        if (!jwtTokenManager.validateToken(token, email)) {
            return "error";
        }

        return "completarPerfilFormulario";  // Página donde se completan los datos (nombre de usuario y contraseña)
    }

   /* @PostMapping("/completar-perfil/{pacienteId}/guardar")
    public ResponseEntity<String> completarPerfil(@PathVariable Long pacienteId, @RequestParam String username, @RequestParam String password, @RequestParam String token) {
        // Verificar que el token es válido
        if (!jwtTokenManager.validateToken(token, pacienteId.toString())) {
            return new ResponseEntity<>("Token inválido", HttpStatus.UNAUTHORIZED);
        }

        // Verificar que el paciente exista
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(pacienteId);
        if (pacienteOptional.isEmpty()) {
            return new ResponseEntity<>("Paciente no encontrado", HttpStatus.NOT_FOUND);
        }

        // Actualizar los datos del paciente
        Paciente paciente = pacienteOptional.get();
        paciente.getUser().setUsername(username);
        paciente.getUser().setPassword(passwordEncoder.encode(password));  // Asegúrate de cifrar la contraseña

        pacienteRepository.save(paciente);

        return new ResponseEntity<>("Perfil completado exitosamente", HttpStatus.OK);
    }*/



    /*@PostMapping("/completar-perfil/{pacienteId}")
    public ResponseEntity<PacienteResponse> completarPerfil(@PathVariable Long pacienteId, @Valid @RequestBody CompletarPerfilPacienteRequest request) {
        // Verificar si el paciente existe
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(pacienteId);
        if (pacienteOptional.isEmpty()) {
            // Si el paciente no existe, devolver 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            // Llamar al servicio que completa el perfil
            PacienteResponse pacienteResponse = completarPerfilPacienteService.completarPerfil(pacienteId, request);
            return new ResponseEntity<>(pacienteResponse, HttpStatus.OK);
        } catch (Exception e) {
            // En caso de error, devolver 500 Internal Server Error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    @PostMapping("/completar-perfil/{pacienteId}")
    public ResponseEntity<String> completarPerfil(@PathVariable Long pacienteId, @RequestParam String username, @RequestParam String password) {
        // Verificar que el paciente exista
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(pacienteId);
        if (pacienteOptional.isEmpty()) {
            return new ResponseEntity<>("Paciente no encontrado", HttpStatus.NOT_FOUND);
        }

        Paciente paciente = pacienteOptional.get();
        // Establecer nombre de usuario y contraseña
        paciente.getUser().setUsername(username);
        paciente.getUser().setPassword(passwordEncoder.encode(password)); // Asegúrate de cifrar la contraseña

        pacienteRepository.save(paciente);

        return new ResponseEntity<>("Perfil completado exitosamente", HttpStatus.OK);
    }


    /* @PostMapping("/desactivar/{pacienteId}")
    public ResponseEntity<Paciente> desactivarPaciente(@PathVariable Long pacienteId) {
        Paciente paciente = pacienteService.desactivarPaciente(pacienteId);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }*/

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


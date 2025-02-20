package com.example.controller;

import com.example.model.Paciente;
import com.example.model.User;
import com.example.repository.PacienteRepository;
import com.example.repository.UserRepository;
import com.example.security.dto.CompletarPerfilPacienteRequest;
import com.example.security.dto.RegistroPacienteRequest;
import com.example.security.jwt.JwtAuthenticationFilter;
import com.example.security.jwt.JwtTokenManager;
import com.example.security.service.CompletarPerfilPacienteService;
import com.example.security.service.UserDetailsServiceImpl;
import com.example.security.service.UserService;
import com.example.service.PacienteService;
import com.example.service.RegistroPacienteService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/paciente")
@CrossOrigin(origins = "http://localhost:3000")
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

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

    @Autowired
    private UserDetailsServiceImpl userDetails;




    @PostMapping("/registrar")
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody RegistroPacienteRequest request) {
        Paciente paciente = registroPacienteService.registrarPaciente(request);
        return new ResponseEntity<>(paciente, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('PACIENTE')")
    @PostMapping("/completar-perfil/{pacienteId}")
    public ResponseEntity<String> completarPerfil(
            @PathVariable Long pacienteId,
            @RequestParam String token,
            @RequestBody CompletarPerfilPacienteRequest request) {

        String email = jwtTokenManager.getEmailFromToken(token);
        if (StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }

        User usuario = userRepository.findByEmail(email);

        if (usuario == null) {
            log.error("usuario no encontrado con el email {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido o usuario no encontrado");
        }

        Paciente paciente = pacienteRepository.findById(pacienteId).orElse(null);
        if (paciente == null || !paciente.getIdPaciente().equals(pacienteId)) {
            log.error("paciente no encontrado o el id no coincide{}", pacienteId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado o ID no coincide");
        }

        paciente.setEstado(true);
        paciente.setPerfilCompletado(true);
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(usuario);
        pacienteRepository.save(paciente);

        return ResponseEntity.ok("Perfil completado con éxito");
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


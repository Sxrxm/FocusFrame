package com.example.service;


import com.example.model.Funcionario;
import com.example.model.User;
import com.example.model.UserRole;
import com.example.repository.FuncionarioRepository;
import com.example.repository.UserRepository;
import com.example.security.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private FuncionarioRepository funcionarioRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    public FuncionarioService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<Funcionario> getFuncionarioById (Long id){
        return funcionarioRepository.findById(id);
    }

    public List<Funcionario> getAllFuncionario (){
        return funcionarioRepository.findAll();
    }


    public User paso1(User user, Locale locale) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        return userRepository.save(user);
    }

    public Funcionario paso2(Long idUsuario, Funcionario funcionario) {

        Optional<User> usuario = userRepository.findById(idUsuario);

        if (usuario.isPresent()) {
            funcionario.setUser(usuario.get());
            return funcionarioRepository.save(funcionario);
        }else {
            throw new RuntimeException("Usuario no encontrado.");
        }
    }
}

package com.example.service;

import com.example.model.Paciente;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private User user;


    public void enviarCorreoConEnlace(String email, String enlace, User user) {
        if (email == null || email.isEmpty()) {
            System.err.println("Error: El correo electrónico es nulo o vacío.");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Completa tu registro");
        message.setText("Hola, " + user.getEmail() +
                "\n\nPara completar tu perfil, haz clic en el siguiente enlace:\n" +
                enlace +
                "\n\nEste enlace te llevará a una página donde podrás crear tu nombre de usuario y establecer una contraseña." +
                "\n\nBienvenido a FocusFrame.");

        try {
            mailSender.send(message);
            System.out.println("Correo enviado a: " + email);
        } catch (MailException e) {
            System.err.println("Error al enviar el correo a " + email + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}

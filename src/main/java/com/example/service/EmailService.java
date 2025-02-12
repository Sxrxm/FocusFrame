package com.example.service;

import com.example.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Método para enviar el correo con el enlace
    public void enviarCorreoConEnlace(String email, String enlace) {
        if (email == null || email.isEmpty()) {
            System.err.println("Error: El correo electrónico es nulo o vacío.");
            return;
        }
        //String enlace = "http://localhost:8080/paciente/completar-perfil?token=" + paciente.getIdPaciente();

        // Crear el mensaje de correo
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Completa tu perfil en el sistema");



        // Personalizar el cuerpo del mensaje, incluyendo el enlace generado
        message.setText("Hola, \n\n" +
                "Para completar tu perfil, haz clic en el siguiente enlace:\n" +
                enlace +
                "\n\nEste enlace te llevará a una página donde podrás crear tu nombre de usuario y establecer una contraseña." +
                "\n\nGracias por utilizar nuestro sistema.");

        // Intentar enviar el correo
        try {
            mailSender.send(message);
            System.out.println("Correo enviado a: " + email);
        } catch (MailException e) {
            // Manejo de excepciones más detallado
            System.err.println("Error al enviar el correo a " + email + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}

package com.realEstate.service.impl;


import com.realEstate.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {


//    public void enviarCorreo(String destino, String asunto, String cuerpo) {
//        SimpleMailMessage mensaje = new SimpleMailMessage();
//        mensaje.setFrom("oscarmayor0211@gmail.com");
//        mensaje.setTo(destino);
//        mensaje.setSubject(asunto);
//        mensaje.setText(cuerpo);
//
//        mailSender.send(mensaje);
//    }


    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreoHtml(String destinatario, String asunto, String contenidoHtml) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true);
            mailSender.send(mensaje);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar correo: " + e.getMessage());
        }
    }

    @Override
    public String cargarTemplate(String nombreArchivo, Map<String, String> variables) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/emails/" + nombreArchivo);
        String contenido = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            contenido = contenido.replace("${" + entry.getKey() + "}", entry.getValue());
        }

        return contenido;
    }



//    public String cargarTemplate(String nombreArchivo, Map<String, String> variables) throws IOException {
//        String ruta = "templates/email/" + nombreArchivo;
//        ClassPathResource resource = new ClassPathResource(ruta);
//        String contenido = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
//
//        for (Map.Entry<String, String> entry : variables.entrySet()) {
//            contenido = contenido.replace("${" + entry.getKey() + "}", entry.getValue());
//        }
//
//        return contenido;
//    }
}
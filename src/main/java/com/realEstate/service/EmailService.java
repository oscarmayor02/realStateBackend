package com.realEstate.service;

import java.io.IOException;
import java.util.Map;

public interface EmailService {
    void enviarCorreoHtml(String destinatario, String asunto, String contenidoHtml);

    String cargarTemplate(String nombreArchivo, Map<String, String> variables) throws IOException;
}

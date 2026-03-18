package org.example.examenspringmvc.service;


import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class OllamaChatService implements ChatService {

    private final OllamaChatModel chatModel;

    public OllamaChatService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    //la pregunta serà la información entregada por stats mas el prompt

    @Override
    public String preguntar(String pregunta) {
        String preguntaLimpia = normalizarTexto(pregunta);

        if (preguntaLimpia.isBlank()) {
            return "";
        }

        String prompt = construirPromptPregunta(preguntaLimpia);
        return consultarModelo(prompt);
    }

    private String construirPromptPregunta(String pregunta) {
        return """
                Responde en HTML limpio y simple, sin incluir <html>, <head> ni <body>.
                Usa solo etiquetas seguras y visuales como:
                <h3>, <p>, <ul>, <li>, <strong>, <em>, <br>, <code>.

                No uses markdown.
                No uses bloques de código.
                No uses ``` .
                No uses scripts.
                No uses estilos inline.
                La respuesta debe ser clara, breve y bien estructurada.

                Pregunta del usuario:
                """ + pregunta;
    }

    private String consultarModelo(String prompt) {
        try {
            String respuesta = chatModel.call(prompt);
            return limpiarRespuestaHtml(respuesta);
        } catch (Exception e) {
            return "<p>Error al contactar con la IA.</p>";
        }
    }

    private String limpiarRespuestaHtml(String respuesta) {
        if (respuesta == null) {
            return "";
        }

        return respuesta
                .replace("```html", "")
                .replace("```HTML", "")
                .replace("```", "")
                .trim();
    }

    private String normalizarTexto(String texto) {
        return texto == null ? "" : texto.trim();
    }
}
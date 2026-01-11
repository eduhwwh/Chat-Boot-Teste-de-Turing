package com.chatbot.demo.controller;

import com.chatbot.demo.model.Persona;
import com.chatbot.demo.service.OpenAIService;
import com.chatbot.demo.service.PersonaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatController {

    private final PersonaService personaService;
    private final OpenAIService openAIService;

    public ChatController(PersonaService personaService, OpenAIService openAIService) {
        this.personaService = personaService;
        this.openAIService = openAIService;
    }

    @GetMapping("/start")
    public Persona start(HttpSession session) {
        Persona persona = personaService.gerarPersona();
        session.setAttribute("persona", persona);
        session.setAttribute("historico", new ArrayList<String>());
        return persona;
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/chat")
    public String chat(@RequestBody String mensagem, HttpSession session) {

        Persona persona = (Persona) session.getAttribute("persona");

        if (persona == null) {
            return "Sessão não iniciada. Acesse /start primeiro.";
        }

        List<String> historico = (List<String>) session.getAttribute("historico");

        String historicoTexto = String.join("\n", historico);

        String resposta = openAIService.conversar(persona, mensagem, historicoTexto);

        historico.add("Usuário: " + mensagem);
        historico.add("Pessoa: " + resposta);

        session.setAttribute("historico", historico);

        return resposta;
    }
}

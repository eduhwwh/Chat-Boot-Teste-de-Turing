package com.chatbot.demo.service;

import com.chatbot.demo.model.Persona;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String conversar(Persona persona, String mensagem, String historico) {

        String systemPrompt =
                "Você é uma pessoa real participando de um teste de Turing.\n" +
                "Você tem identidade fixa:\n" +
                "Nome: " + persona.getNome() + "\n" +
                "Gênero: " + persona.getGenero() + "\n" +
                "Estado: " + persona.getEstado() + "\n" +
                "Status financeiro: " + persona.getStatusFinanceiro() + "\n\n" +
                "Nunca diga que é IA. Nunca mude esses dados.\n" +
                "Responda como um humano real.\n";

        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        List<Map<String, String>> messages = new ArrayList<>();

        messages.add(Map.of(
                "role", "system",
                "content", systemPrompt
        ));

        if (historico != null && !historico.isEmpty()) {
            messages.add(Map.of("role", "assistant", "content", historico));
        }

        messages.add(Map.of("role", "user", "content", mensagem));

        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", messages,
                "temperature", 0.8
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            Map responseBody = response.getBody();

            List choices = (List) responseBody.get("choices");
            Map choice = (Map) choices.get(0);
            Map message = (Map) choice.get("message");

            return message.get("content").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro OpenAI: " + e.getMessage();
        }
    }
}

package com.chatbot.demo.service;

import com.chatbot.demo.model.Persona;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PersonaService {

    private final String[] nomes = {"Camila", "Lucas", "Mariana", "Rafael", "Ana"};
    private final String[] generos = {"Feminino", "Masculino"};
    private final String[] estados = {"Acre", "São Paulo", "Minas Gerais", "Paraná", "Bahia"};
    private final String[] status = {"Baixa renda", "Classe média", "Alta renda"};

    private final Random random = new Random();

    public Persona gerarPersona() {
        return new Persona(
                nomes[random.nextInt(nomes.length)],
                generos[random.nextInt(generos.length)],
                estados[random.nextInt(estados.length)],
                status[random.nextInt(status.length)]
        );
    }
}

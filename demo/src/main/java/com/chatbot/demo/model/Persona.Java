package com.chatbot.demo.model;

public class Persona {

    private String nome;
    private String genero;
    private String estado;
    private String statusFinanceiro;

    public Persona(String nome, String genero, String estado, String statusFinanceiro) {
        this.nome = nome;
        this.genero = genero;
        this.estado = estado;
        this.statusFinanceiro = statusFinanceiro;
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public String getEstado() {
        return estado;
    }

    public String getStatusFinanceiro() {
        return statusFinanceiro;
    }
}

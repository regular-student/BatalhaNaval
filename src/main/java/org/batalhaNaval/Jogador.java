package org.batalhaNaval;

public class Jogador {
    private String nome;
    private Mapa mapa;
    //falta uma lista de embarcações

    public Jogador(String nome) {
        this.nome = nome;
        this.mapa = new Mapa;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public String getNome() {
        return nome;
    }
}

package org.batalhaNaval;

import Navios.*;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private final String nome;
    private final Mapa mapa;
    private final List<Embarcacao> embarcacoes = new ArrayList<>();

    public Jogador(String nome) {
        this.nome = nome;
        this.mapa = new Mapa();
        inicializarNavios();
    }

    public void inicializarNavios() {
        List<Embarcacao> tipos = new ArrayList<>();
        tipos.add(new Bote());
        tipos.add(new Destroyer());
        tipos.add(new Fragata());
        tipos.add(new PortaAvioes());
        tipos.add(new Submarino());
        int indice = 0;

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < i; j++) {
                embarcacoes.add(tipos.get(indice));
            }
            indice++;
        }
    }

    public Mapa getMapa() {
        return mapa;
    }

    public String getNome() {
        return nome;
    }

    public List<Embarcacao> getEmbarcacoes() {
        return embarcacoes;
    }

    public void imprimirNavios() {
        for (Embarcacao e : embarcacoes) {
            System.out.println(e.getClass().getSimpleName());
        }
    }
}

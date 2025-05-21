package org.batalhaNaval;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Navios.Bote;
import Navios.Destroyer;
import Navios.Embarcacao;
import Navios.Fragata;
import Navios.PortaAvioes;
import Navios.Submarino;

public class Jogo {
    private Jogador jogador1;
    private Jogador jogador2;
    private Scanner leitor = new Scanner(System.in);
    private final Mapa mapa;
    private final List<Embarcacao> embarcacoes = new ArrayList<>();

    public Jogo(String nome1, String nome2) {
        this.jogador1 = new Jogador(nome1);
        this.jogador2 = new Jogador(nome2);
        this.mapa = new Mapa();
        inicializarNavios();
        this.mapa.posicionar(embarcacoes);
        mapa.imprimirMI();
        getEmbarcacoes();
        getMapa();
        imprimirNavios();
        rodada();
    }

    public void inicializarNavios() {
        List<Embarcacao> tipos = new ArrayList<>();
        tipos.add(new PortaAvioes());
        tipos.add(new Destroyer());
        tipos.add(new Submarino());
        tipos.add(new Fragata());
        tipos.add(new Bote());
        
        
        
        int indice = 0;

        for (int i = 2; i <= 6; i++) {
            for (int j = 0; j < i; j++) {
                embarcacoes.add(tipos.get(indice));
            }
            indice++;
        }
    }

    public Mapa getMapa() {
        return mapa;
    }

    public List<Embarcacao> getEmbarcacoes() {
        return embarcacoes;
    }

    public void imprimirNavios() {
        for (Embarcacao e : embarcacoes) {
            System.out.println(e.getClass().getSimpleName());
        }
    }

    public void rodada() {
        Jogador atual = jogador1;
    
        while (jogador1.getPontos() < 6 && jogador2.getPontos() < 6) {
            System.out.println("Vez de " + atual.getNome() + ". Escolha a linha (0-15) e a coluna (0-15):");
            int x = leitor.nextInt();
            int y = leitor.nextInt();
    
            if (x < 0 || x >= 16 || y < 0 || y >= 16) {
                System.out.println("Coordenadas inválidas! Tente novamente.");
                continue;
            }
    
            boolean acertou = mapa.atacar(x, y);
            mapa.marcar(x, y, acertou ? 'X' : '~');
            mapa.imprimir();
    
            if (acertou) {
                atual.adicionarPonto();
                System.out.println("Acertou!");
            } else {
                System.out.println("Errou!");
                // troca o jogador
                atual = (atual == jogador1) ? jogador2 : jogador1;
            }
        }
    
        String vencedor = jogador1.getPontos() >= 6 ? jogador1.getNome() : jogador2.getNome();
        System.out.println("Vitória de " + vencedor + "!");
}
}

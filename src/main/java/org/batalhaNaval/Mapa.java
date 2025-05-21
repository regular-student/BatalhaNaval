package org.batalhaNaval;

import java.util.List;
import java.util.Random;

import Navios.Embarcacao;

public class Mapa {
    private final int linhas = 16;
    private final int colunas = 16;

    private final char[][] mapaVisivel = new char[linhas][colunas];
    private final char[][] mapaInterno = new char[linhas][colunas];

    public Mapa() {
        inicializarMapas();
    }
    
    public void inicializarMapas() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                mapaVisivel[i][j] = 'O';
                mapaInterno[i][j] = 'V';
            }
        }
    }


    public void posicionar(List<Embarcacao> embarcacoes) {
        Random rand = new Random();
        int id = 0;

        for (Embarcacao e : embarcacoes) {
            boolean colocado = false;
            int tentativas = 0;

            while (!colocado && tentativas < 1000) {
                int x = rand.nextInt(linhas);
                int y = rand.nextInt(colunas);
                int direcao = rand.nextInt(3); // 0 = horizontal, 1 = vertical, 2 = diagonal

                if (podeColocar(x, y, e.getTamanho(), direcao)) {
                    colocar(x, y, e, direcao, id);
                    colocado = true;
                }

                tentativas++;
            }

            if (!colocado) {
                System.out.println("Falha ao posicionar navio: " + e.getClass().getSimpleName());
            }
        }
    }

    // Pega os barcos da classe anterior testa se da pra colocar no mapa
    private boolean podeColocar(int x, int y, int tamanho, int direcao) {
        int dx = 0, dy = 0;

        switch(direcao) {
            case 0 -> dy = 1; //horizontal
            case 1 -> dx = 1; //vertical
            case 2 -> { dx = 1; dy = 1; } //diagonal
            default ->  throw new IllegalArgumentException("Direção invalida");
        }

        int fimX = x + dx * (tamanho - 1);
        int fimY = y + dy * (tamanho - 1);
        if (fimX >= linhas || fimY >= colunas) return false;

        for (int i = 0; i < tamanho; i++) {
            int nx = x + dx * i;
            int ny = y + dy * i;
            if (mapaInterno[nx][ny] != 'V') return false; // já ocupado
        }

        return true;
    }

    private void colocar(int x, int y, Embarcacao e, int direcao, int id) {
        int dx = 0, dy = 0;

        switch (direcao) {
            case 0 -> dy = 1; // horizontal
            case 1 -> dx = 1; // vertical
            case 2 -> {
                dx = 1;
                dy = 1;
            }
        }

        for (int i = 0; i < e.getTamanho(); i++) {
            int nx = x + dx * i;
            int ny = y + dy * i;
            mapaInterno[nx][ny] = e.getNome();
        }
       
    }

    public boolean atacar(int x, int y) {
        if (mapaInterno[x][y] != 'V') {
            mapaInterno[x][y] = 'Y';
            return true;
        }

        return false;
    }

    public void marcar(int x, int y, char afundar) {
        mapaVisivel[x][y] = afundar;
    }

    public void imprimir() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(mapaVisivel[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void imprimirMI() {
        //imprime o mapa interno pra debug
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(mapaInterno[i][j] + " ");
            }
            System.out.println();
        }
    }

}

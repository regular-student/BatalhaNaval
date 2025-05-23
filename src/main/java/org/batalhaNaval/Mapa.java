package org.batalhaNaval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Navios.Embarcacao;

public class Mapa {
    private final int linhas = 16;
    private final int colunas = 16;

    private final char[][] mapaVisivel = new char[linhas][colunas];
    private final char[][] mapaInterno = new char[linhas][colunas];

    private List<Embarcacao> embarcacoesNoMapa = new ArrayList<>();

    public Mapa() {
        inicializarMapas();
        System.out.println("=== DEBUG - POSIÇÕES DOS NAVIOS ===");
        for (Embarcacao e : embarcacoesNoMapa) {
            System.out.print(e.getNome() + ": ");
            for (int[] pos : e.getPosicoes()) {
                System.out.print("(" + pos[0] + "," + pos[1] + ") ");
            }
            System.out.println();
        }
        System.out.println("=================================");
    }
    
    public void inicializarMapas() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                mapaVisivel[i][j] = 'O';
                mapaInterno[i][j] = '~';
            }
        }
    }


    public void posicionar(List<Embarcacao> embarcacoes) {
        Random rand = new Random();

        for (Embarcacao e : embarcacoes) {
            boolean colocado = false;
            int tentativas = 0;

            System.out.println("Posicionando " + e.getNome() + "...");

            while (!colocado && tentativas < 1000) {
                int x = rand.nextInt(linhas);
                int y = rand.nextInt(colunas);
                int direcao = rand.nextInt(3);

                if (podeColocar(x, y, e.getTamanho(), direcao)) {
                    System.out.println("Colocando " + e.getNome() + " em (" + x + "," + y + ") direção " + direcao);
                    colocar(x, y, e, direcao);
                    colocado = true;

                    // Debug: mostrar posições adicionadas
                    System.out.println("Posições adicionadas:");
                    for (int[] pos : e.getPosicoes()) {
                        System.out.printf("(%d,%d) ", pos[0], pos[1]);
                    }
                    System.out.println();
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
            case 2 -> { dx = 1; dy = 1; } //diagonal direita
            default -> throw new IllegalArgumentException("Direção invalida");
        }

        // Verificar limites do mapa
        int fimX = x + dx * (tamanho - 1);
        int fimY = y + dy * (tamanho - 1);
        if (fimX >= linhas || fimY >= colunas) return false;

        // Verificar se as posições estão livres E sem navios adjacentes
        for (int i = 0; i < tamanho; i++) {
            int nx = x + dx * i;
            int ny = y + dy * i;

            // Verifica se a posição está livre
            if (mapaInterno[nx][ny] != '~') return false;

            // Verifica adjacentes (incluindo diagonais)
            for (int adjX = nx - 1; adjX <= nx + 1; adjX++) {
                for (int adjY = ny - 1; adjY <= ny + 1; adjY++) {
                    if (adjX >= 0 && adjX < linhas && adjY >= 0 && adjY < colunas) {
                        if (mapaInterno[adjX][adjY] != '~') {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void colocar(int x, int y, Embarcacao e, int direcao) {
        int dx = 0, dy = 0;

        switch (direcao) {
            case 0 -> dy = 1; // horizontal
            case 1 -> dx = 1; // vertical
            case 2 -> { dx = 1; dy = 1; } // diagonal
        }

        // Limpa posições existentes (segurança)
        e.getPosicoes().clear();

        // Adiciona novas posições
        for (int i = 0; i < e.getTamanho(); i++) {
            int nx = x + dx * i;
            int ny = y + dy * i;
            mapaInterno[nx][ny] = e.getNome();
            e.adicionarPosicao(nx, ny);
            System.out.println("Adicionado posição (" + nx + "," + ny + ")");
        }

        embarcacoesNoMapa.add(e);
    }

    public boolean atacar(int x, int y) {
        // Verifica se a posição já foi atacada
        if (mapaVisivel[x][y] != 'O') {
            return false;
        }

        // Se for água
        if (mapaInterno[x][y] == '~') {
            mapaVisivel[x][y] = 'A';
            return false;
        }
        // Se acertou um navio
        else {
            char simboloNavio = mapaInterno[x][y];

            // Encontra a embarcação específica que foi atingida
            Embarcacao embarcacaoAtingida = null;
            for (Embarcacao e : embarcacoesNoMapa) {
                if (e.contemPosicao(x, y)) {
                    embarcacaoAtingida = e;
                    break;
                }
            }
            // No método atacar(), após encontrar a embarcação
//            System.out.println("Posições da embarcação " + embarcacaoAtingida.getNome() + ":");
//            for (int[] pos : embarcacaoAtingida.getPosicoes()) {
//                System.out.printf("(%d,%d) ", pos[0], pos[1]);
//            }
//            System.out.println();

            // Se encontrou a embarcação
            if (embarcacaoAtingida != null) {
                // Marca todas as posições desta embarcação como destruída
                for (int[] pos : embarcacaoAtingida.getPosicoes()) {
                    mapaVisivel[pos[0]][pos[1]] = 'X';
                }

                System.out.println("Você DESTRUIU uma embarcação!");
                embarcacoesNoMapa.remove(embarcacaoAtingida);
                return true;
            }

            // Caso não encontre (não deveria acontecer)
            mapaVisivel[x][y] = 'X';
            return true;
        }
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

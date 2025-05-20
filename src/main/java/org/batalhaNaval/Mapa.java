package org.batalhaNaval;

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


    public boolean inserir(int x, int y, int tamanho, boolean horizontal) {
        // Validação e posicionamento
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
                System.out.println(mapaVisivel[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void imprimirMI() {
        //imprime o mapa interno pra debug
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.println(mapaInterno[i][j] + " ");
            }
            System.out.println();
        }
    }

}

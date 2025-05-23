package Navios;

import java.util.ArrayList;
import java.util.List;

public abstract class Embarcacao {
    protected int tamanho;
    protected char nome;
    private final List<int[]> posicoes;

    public Embarcacao(int tamanho, char nome) {
        this.tamanho = tamanho;
        this.nome = nome;
        this.posicoes = new ArrayList<>();
    }

    public void adicionarPosicao(int x, int y) {
        posicoes.add(new int[]{x, y});
    }

    public boolean foiAtingido(int x, int y) {
        for (int[] pos : posicoes) {
            if (pos[0] == x && pos[1] == y) {
                return true;
            }
        }
        return false;
    }

    public int getTamanho() {
        return tamanho;
    }

    public char getNome() {
        return nome;
    }

    public List<int[]> getPosicoes() {
        return posicoes;
    }

    public boolean contemPosicao(int x, int y) {
        for (int[] pos : posicoes) {
            if (pos[0] == x && pos[1] == y) return true;
        }
        return false;
    }

}

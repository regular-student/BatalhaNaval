package Navios;

public abstract class Embarcacao {
    protected int tamanho;
    protected char nome;
    protected boolean afundada = false;

    public Embarcacao(int tamanho, char nome) {
        this.tamanho = tamanho;
        this.nome = nome;
    }

    public int getTamanho() {
        return tamanho;
    }

    public char getNome() {
        return nome;
    }

    public boolean isAfundada() {
        return afundada;
    }

    public void setAfundada(boolean afundada) {
        this.afundada = afundada;
    }

}

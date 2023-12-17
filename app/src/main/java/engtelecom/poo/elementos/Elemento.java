package engtelecom.poo.elementos;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Representa um elemento genérico de jogo.
 */
public abstract class Elemento {
    protected int coordenadaX;
    protected int coordenadaY;
    protected int velocidadeX;
    protected int velocidadeY;
    protected int altura;
    protected int largura;
    protected Color cor;

    public Elemento(int coordenadaX, int coordenadaY, int velocidadeX, int velocidadeY, int altura, int largura,
            Color cor) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.velocidadeX = velocidadeX;
        this.velocidadeY = velocidadeY;
        this.altura = altura;
        this.largura = largura;
        this.cor = cor;
    }

    public Elemento(int coordenadaX, int coordenadaY, int altura, int largura, Color cor) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.velocidadeX = 0;
        this.velocidadeY = 0;
        this.altura = altura;
        this.largura = largura;
        this.cor = cor;
    }

    /**
     * Desenha um elemento na tela
     * 
     * @param g2d O objeto gráfico de desenho
     */
    public abstract void desenhar(Graphics2D g2d);

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public int getVelocidadeX() {
        return velocidadeX;
    }

    public int getVelocidadeY() {
        return velocidadeY;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public Color getCor() {
        return cor;
    }

    public void setVelocidadeX(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }

    public void setVelocidadeY(int velocidadeY) {
        this.velocidadeY = velocidadeY;
    }

}

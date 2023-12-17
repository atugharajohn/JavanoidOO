package engtelecom.poo.elementos;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Bolinha que se movimenta automaticamente no jogo, sem alteração do usuário.
 */
public class Bolinha extends Elemento implements Movimenta {

    // configurações da bolinha
    private static final int COORD_X_INICIAL = 150;
    private static final int COORD_Y_INICIAL = 20;
    private static final int VELOCIDADE_X = 4;
    private static final int VELOCIDADE_Y = 4;
    private static final int DIMENSAO = 25;
    private static final Color COR = Color.WHITE;

    public Bolinha() {
        super(COORD_X_INICIAL, COORD_Y_INICIAL, VELOCIDADE_X, VELOCIDADE_Y, DIMENSAO, DIMENSAO, COR);
    }

    public void resetarPosicao() {
        this.coordenadaX = COORD_X_INICIAL;
        this.coordenadaY = COORD_Y_INICIAL;
    }

    public void setVelocidadeX(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }

    public void setVelocidadeY(int velocidadeY) {
        this.velocidadeY = velocidadeY;
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

    /**
     * Desenha a bolinha na tela
     * 
     * @param O objeto gráfico de desenho
     */
    @Override
    public void desenhar(Graphics2D g2d) {
        g2d.setColor(this.cor);
        g2d.fillOval(this.coordenadaX, this.coordenadaY, this.largura, this.altura);
    }

    /**
     * Movimenta a bolinha.
     */
    @Override
    public void movimentar() {
        this.coordenadaX += this.velocidadeX;
        this.coordenadaY += this.velocidadeY;
    }

}

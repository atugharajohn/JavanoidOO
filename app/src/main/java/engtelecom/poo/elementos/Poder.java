package engtelecom.poo.elementos;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Representa o poder que está contido dentro de um tijolo.
 */
public class Poder extends Elemento implements Movimenta {

    public static final int VELOCIDADE_X_INICIAL = 0;
    public static final int VELOCIDADE_Y_INICIAL = 2;
    public static final int ALTURA_PODER = 25;
    public static final int LARGURA_PODER = 25;
    public static final Color COR_PODER = Color.RED;

    /**
     * Tipo específico do poder, como aumento de velocidade ou vida extra.
     */
    private TipoPoder tipoPoder;

    /**
     * Indica se o poder está atualmente visível na tela.
     */
    private boolean apareceNaTela;

    /**
     * Indica se o poder foi capturado por um elemento no jogo.
     */
    private boolean capturado;

    public Poder(int id, int coordenadaX, int coordenadaY, int velocidadeX, int velocidadeY, int altura, int largura,
            Color cor) {
        super(coordenadaX, coordenadaY, velocidadeX, velocidadeY, ALTURA_PODER, LARGURA_PODER, COR_PODER);
        this.tipoPoder = TipoPoder.getById(id);
        this.apareceNaTela = false;
        this.capturado = false;
    }

    public void setPoder(int id) {
        this.tipoPoder = TipoPoder.getById(id);
    }

    /**
     * Desenha o poder na tela se ele estiver em estado de desenho disponível
     */
    @Override
    public void desenhar(Graphics2D g2d) {
        if (apareceNaTela) {
            movimentar();
            g2d.setColor(this.cor);
            g2d.fillRect(this.coordenadaX, this.coordenadaY, this.largura, this.altura);
            g2d.setColor(tipoPoder.cor);
            g2d.fillRect(this.coordenadaX, this.coordenadaY, this.largura / 2, this.altura / 2);
        }
    }

    /**
     * Movimenta o poder.
     */
    @Override
    public void movimentar() {
        if (apareceNaTela) {
            coordenadaY += velocidadeY;
        }
    }

    public void ligarEstadoDesenho() {
        this.apareceNaTela = true;
    }

    public void desligarEstadoDesenho() {
        this.apareceNaTela = false;
    }

    public TipoPoder getTipoPoder() {
        return this.tipoPoder;
    }

    public void capturar() {
        this.capturado = true;
    }

    public boolean jaFoiCapturado() {
        if (this.capturado) {
            return true;
        }
        return false;
    }
}

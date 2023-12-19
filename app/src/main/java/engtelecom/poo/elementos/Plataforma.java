package engtelecom.poo.elementos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * Representa a plataforma que o usuário (jogador) pode movimentar durante o
 * jogo.
 */
public class Plataforma extends Elemento implements Movimenta {

    private Image imagem;

    private final static int COORDENADA_X_INICIAL = 100;
    private final static int COORDENADA_Y_INICIAL = 520; // 540
    private final static int ALTURA = 25;

    private static final int TAM_ORIGINAL = 100;
    private static final double FATOR_TAMANHO_LIMITE = 0.2; // tamanho original não pode exceder/ter menos que esse
                                                            // fator tamanho
    private static final double FATOR_MUDANCA = 0.1; // o quanto aumenta por vez de poderes

    public Plataforma() {
        super(COORDENADA_X_INICIAL, COORDENADA_Y_INICIAL, 0, 0, ALTURA, TAM_ORIGINAL);
    }

    @Override
    public void desenhar(Graphics2D g2d) {

        this.imagem = carregarImagem("imagens/plataforma.png");
        g2d.drawImage(imagem, this.coordenadaX, this.coordenadaY, this.largura, this.altura, null);
        // g2d.setColor(this.cor);
        // g2d.fillRect(this.coordenadaX, this.coordenadaY, this.largura, this.altura);
    }

    /**
     * Aumenta o tamanho da plataforma de acordo com os limites possíveis.
     */
    public void aumentarTamanho() {
        int novaLargura = this.largura + (int) (this.largura * FATOR_MUDANCA);
        int limiteMax = (int) (TAM_ORIGINAL + TAM_ORIGINAL * FATOR_TAMANHO_LIMITE);
        if (novaLargura < limiteMax) {
            this.largura = novaLargura;
        }
    }

    /**
     * Diminui o tamanho da plataforma de acordo com os limites possíveis.
     */
    public void diminuirTamanho() {
        int novaLargura = this.largura - (int) (this.largura * FATOR_MUDANCA);
        int limiteMin = (int) (TAM_ORIGINAL - TAM_ORIGINAL * FATOR_TAMANHO_LIMITE);
        if (novaLargura > limiteMin) {
            this.largura = novaLargura;
        }
    }

    /**
     * Movimenta a plataforma.
     */
    @Override
    public void movimentar() {
        this.coordenadaX += this.velocidadeX;
    }

    public void setVelocidadeX(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }

    public int getVelocidadeX() {
        return velocidadeX;
    }

}

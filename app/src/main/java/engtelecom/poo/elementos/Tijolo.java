package engtelecom.poo.elementos;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Representa um bloco de uma matriz de tijolos.
 */
public class Tijolo extends Elemento {
    public TipoTijolo modelo;
    private Poder poder;
    private int durabilidadeAtual;
    private int posicao;

    public static final int LARGURA_TIJOLO = 60;
    public static final int ALTURA_TIJOLO = 30;

    public Tijolo(int posicao, int id, int coordenadaX, int coordenadaY, int altura, int largura, Color cor) {
        super(coordenadaX, coordenadaY, altura, largura, cor);
        this.modelo = TipoTijolo.getById(id);
        this.durabilidadeAtual = modelo.durabilidadeTotal;
        this.posicao = posicao;
    }

    public int getPosicao() {
        return posicao;
    }

    /**
     * Derruba um tijolo, ou seja, permite ao poder ser desenhado e movimentado.
     */
    private void derrubarPoder() {
        Poder p = this.poder;
        if (p != null) {
            this.poder.ligarEstadoDesenho();
            this.poder.movimentar();
        }
    }

    /**
     * Diminui a durabilidade atual do tijolo e, se ela for zero, o tijolo pode ser
     * derrubado se contiver um poder.
     *
     * @return Verdadeiro se o tijolo puder ser derrubado e Falso caso contrário.
     */
    public boolean diminuirDurabilidadeAtual() {
        if (durabilidadeAtual > 0) {
            durabilidadeAtual--;

            // libera o poder
            if (durabilidadeAtual == 0) {
                derrubarPoder();
            }
            return true;
        }
        return false;
    }

    public void setPoder(Poder poder) {
        this.poder = poder;
    }

    public int getDurabilidadeAtual() {
        return durabilidadeAtual;
    }

    public TipoTijolo getModelo() {
        return this.modelo;
    }

    /**
     * Desenha o poder na tela se ele estiver em estado possível de desenho.
     * 
     * @param g2d O objeto de desenho gráfico
     */
    @Override
    public void desenhar(Graphics2D g2d) {
        if (durabilidadeAtual > 0) {
            g2d.setColor(modelo.cor);
            g2d.fillRect(this.coordenadaX, this.coordenadaY, this.largura, this.altura);
        }

        if (this.poder != null) {
            this.poder.desenhar(g2d);
        }
    }

    public Poder getPoder() {
        return this.poder;
    }

}

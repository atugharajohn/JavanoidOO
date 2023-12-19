package engtelecom.poo.elementos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;

import java.net.URL;
import javax.swing.ImageIcon;

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
    protected Image imagem;

    public Elemento(int coordenadaX, int coordenadaY, int velocidadeX, int velocidadeY, int altura, int largura) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.velocidadeX = velocidadeX;
        this.velocidadeY = velocidadeY;
        this.altura = altura;
        this.largura = largura;
    }

    public Elemento(int coordenadaX, int coordenadaY, int altura, int largura) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.velocidadeX = 0;
        this.velocidadeY = 0;
        this.altura = altura;
        this.largura = largura;
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

    public void setVelocidadeX(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }

    public void setVelocidadeY(int velocidadeY) {
        this.velocidadeY = velocidadeY;
    }

    /**
     * Carrega uma imagem
     * 
     * @param arquivo nome do arquivo de imagem que deve estar na pasta
     *                /src/main/resources
     * @return imagem carregada
     */
    public Image carregarImagem(String arquivo) {
        try {
            var ii = new ImageIcon(getClass().getResource("/" + arquivo));

            if ((ii == null) || (ii.getImageLoadStatus() != MediaTracker.COMPLETE)) {
                URL url = getClass().getResource("/" + arquivo);
                if (url == null)
                    throw new IllegalArgumentException("Imagem " + arquivo + " não encontrada");
                ii = new ImageIcon(url);
            }
            return ii.getImage();
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}

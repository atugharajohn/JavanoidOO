package engtelecom.poo.areas;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

import java.awt.Image;
import java.awt.MediaTracker;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Classe abstrata que representa uma área gráfica
 */
public abstract class Area {
    protected int coordenadaX;
    protected int coordenadaY;
    protected int largura;
    protected int altura;
    protected Image imagem;

    public Area(int coordenadaX, int coordenadaY, int largura, int altura) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.largura = largura;
        this.altura = altura;
    }

    /**
     * Desenha uma área na tela.
     * 
     * @param g2d Objeto gráfico para desenhos.
     */
    public abstract void desenharArea(Graphics2D g2d);

    /**
     * Carrega uma fonte do disco a partir do diretório "/fontes/".
     *
     * @param nome    O nome do arquivo da fonte.
     * @param tamanho O tamanho da fonte.
     * @return A fonte carregada.
     */
    public Font carregaFonteDoDisco(String nome, float tamanho) {
        InputStream is = getClass().getResourceAsStream("/fontes/" + nome);
        try {
            var font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(tamanho);
            return font;
        } catch (FontFormatException | IOException e) {
            System.err.println("erro ao ler font do disco: " + e);
        }
        return null;
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

package engtelecom.poo.areas;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

/**
 * Classe abstrata que representa uma área gráfica
 */
public abstract class Area {
    protected int coordenadaX;
    protected int coordenadaY;
    protected int largura;
    protected int altura;
    protected Color cor;

    public Area(int coordenadaX, int coordenadaY, int largura, int altura, Color cor) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.largura = largura;
        this.altura = altura;
        this.cor = cor;
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
}

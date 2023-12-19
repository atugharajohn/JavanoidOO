package engtelecom.poo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import engtelecom.poo.areas.Area;
import engtelecom.poo.areas.AreaJogo;
import engtelecom.poo.areas.AreaPainel;
import engtelecom.poo.areas.AreaPlacar;

/**
 * Classe para criar e gerenciar interface gráfica.
 */
public class JavanoidOO extends JPanel {

    private static final int FPS = 60;

    // para tela completa
    public final static int LARGURA_TELA = 800;
    public final static int ALTURA_TELA = 600;

    // para área do jogo
    public static final int AREA_JOGO_X = 0;
    public static final int AREA_JOGO_Y = 0;
    public static final int AREA_JOGO_LARGURA = 500;
    public static final int AREA_JOGO_ALTURA = 600;
    public static final Color AREA_JOGO_COR = Color.BLACK;

    // para área do placar
    public static final int AREA_PLACAR_X = 500;
    public static final int AREA_PLACAR_Y = 0;
    public static final int AREA_PLACAR_LARGURA = 300;
    public static final int AREA_PLACAR_ALTURA = 600;
    public static final Color AREA_PLACAR_COR = Color.LIGHT_GRAY;

    // para área do painel
    public final static int AREA_PAINEL_X = 0;
    public final static int AREA_PAINEL_Y = 0;
    public final static int AREA_PAINEL_LARGURA = 800;
    public final static int AREA_PAINEL_ALTURA = 600;
    public final static Color AREA_PAINEL_COR = Color.LIGHT_GRAY;

    private ArrayList<Area> areas;
    private Timer timer;

    public JavanoidOO() {
        areas = new ArrayList<>();
        areas.add(new AreaJogo(AREA_JOGO_X, AREA_JOGO_Y, AREA_JOGO_LARGURA, AREA_JOGO_ALTURA));
        areas.add(
                new AreaPlacar(AREA_PLACAR_X, AREA_PLACAR_Y, AREA_PLACAR_LARGURA, AREA_PLACAR_ALTURA));
        areas.add(
                new AreaPainel(AREA_PAINEL_X, AREA_PAINEL_Y, AREA_PAINEL_LARGURA, AREA_PAINEL_ALTURA));
    }

    /**
     * Inicialização da janela de desenhos.
     */
    public void iniciar() {
        JFrame frame = new JFrame();
        frame.setSize(LARGURA_TELA, ALTURA_TELA);
        frame.setTitle("JavanoidOO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // não permite que o usuário redimensione a janela
        frame.setResizable(false);

        // centraliza a janela na tela
        frame.setLocationRelativeTo(null);

        // adiciona o painel JavanoidOO ao frame
        frame.add(this);

        frame.addKeyListener((KeyListener) areas.get(0));

        // Torna o frame focável para que o KeyListener funcione
        frame.setFocusable(true);

        // exibe a janelalataforma
        frame.setVisible(true);

        // invoca o método paint() dessa classe a cada 1000/FPS milissegundos
        this.timer = new Timer(1000 / FPS, e -> {
            repaint();
        });
        // disparando o timer
        this.timer.start();

    }

    /**
     * Desenha áreas do jogo.
     * 
     * @param g Objeto gráfico utilizado para desenhos.
     */
    @Override
    public void paint(Graphics g) {
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // desenha cada área individualmente
        for (var area : areas) {
            area.desenharArea(g2d);
        }

        // sincroniza o contexto gráfico
        Toolkit.getDefaultToolkit().sync();
    }

}
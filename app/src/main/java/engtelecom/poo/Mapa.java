package engtelecom.poo;

import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import engtelecom.poo.elementos.Poder;
import engtelecom.poo.elementos.Tijolo;
import engtelecom.poo.elementos.TipoTijolo;

/**
 * Representa o mapa do jogo, contendo os tijolos, poderes e métodos
 * relacionados.
 */
public class Mapa {
    private Tijolo[][] tijolos;
    private ArrayList<Integer> tipoTijolos;
    private int numeroPoderes;
    private int numeroColunas;
    private int numeroLinhas;
    private int fatorX;
    private int fatorY;
    private int nivel; // TODO nível como uma enum

    private final static int MAX_NIVEL = 4;
    private final static int MIN_NIVEL = 1;

    /**
     * Construtor da classe Mapa.
     *
     * @param coordenadaX Coordenada X inicial do mapa.
     * @param coordenadaY Coordenada Y inicial do mapa.
     */
    public Mapa(int coordenadaX, int coordenadaY) {
        this.nivel = MIN_NIVEL;
        this.tipoTijolos = new ArrayList<>();

        selecionaMapa();
    }

    /**
     * Seleciona o mapa correspondente ao nível atual.
     *
     */
    private void selecionaMapa() {
        String caminhoArquivo = "";
        switch (this.nivel) {
            case 1:
                caminhoArquivo = "/niveis/n1.csv";
                break;
            case 2:
                caminhoArquivo = "/niveis/n2.csv";
                break;
            case 3:
                caminhoArquivo = "/niveis/n3.csv";
                break;
            case 4:
                caminhoArquivo = "/niveis/n4.csv";
        }
        try {
            ler(caminhoArquivo);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Avança o nível atual do jogo.
     * 
     */
    public void avancarNivel() {
        if (this.nivel < MAX_NIVEL) {
            this.nivel++;
        } else {
            this.nivel = MIN_NIVEL;
        }
        selecionaMapa();

    }

    /**
     * Reseta o nível do jogo para o nível inicial.
     */
    public void resetarNivel() {
        this.nivel = MIN_NIVEL;
        selecionaMapa();

    }

    /**
     * Lê o arquivo contendo o nível atual do jogo.
     */
    private void ler(String nomeDoArquivo) throws IOException {
        try (InputStream is = this.getClass().getResourceAsStream(nomeDoArquivo);
                Scanner linha = new Scanner(is)) {
            // primeira linha: número de poderes
            String s = linha.nextLine();
            String[] numeroElementos = s.split(",");
            this.numeroPoderes = Integer.parseInt(numeroElementos[0]);

            // segunda linha: tipos de tijolos
            String s2 = linha.nextLine();
            String[] tipoTijolos = s2.split(",");
            for (var tijolo : tipoTijolos) {
                this.tipoTijolos.add(Integer.parseInt(tijolo));
            }

            // terceira linha: fatores X e Y
            String s3 = linha.nextLine();
            String[] fatores = s3.split(",");
            this.fatorX = Integer.parseInt(fatores[0]);
            this.fatorY = Integer.parseInt(fatores[1]);

            // quarta linha: número de linhas e colunas
            String s4 = linha.nextLine();
            String[] matriz = s4.split(",");
            this.numeroLinhas = Integer.parseInt(matriz[0]);
            this.numeroColunas = Integer.parseInt(matriz[1]);

            preencherMapa();

        } catch (FileNotFoundException e) {
            System.err.println("arquivo não encontrado: " + e);
        }
    }

    /**
     * Sorteia tijolos para terem poderes de acordo com o número máximo de poderes
     * do nível atual.
     */
    private void sorteiaPoderesNosTijolos() {
        Random rand = new Random();
        int numeroSorteios = this.numeroPoderes;

        while (numeroSorteios > 0) {
            // sorteia coluna e linha para um tijolo com poder
            int coluna = rand.nextInt(this.numeroColunas);
            int linha = rand.nextInt(this.numeroLinhas);

            Tijolo tijoloSorteado = this.tijolos[linha][coluna];

            // verifica s eo tijolo sorteado é válido para receber um poder
            if (tijoloSorteado != null && tijoloSorteado.getPoder() == null
                    && tijoloSorteado.getModelo() != TipoTijolo.INDESTRUTIVEL) {
                // sorteia um poder
                int poderId = rand.nextInt(3) + 1;

                // adiciona um poder ao tijolo
                tijoloSorteado.setPoder(new Poder(poderId, tijoloSorteado.getCoordenadaX(),
                        tijoloSorteado.getCoordenadaY(), Poder.VELOCIDADE_X_INICIAL,
                        Poder.VELOCIDADE_Y_INICIAL, Poder.ALTURA_PODER, Poder.LARGURA_PODER,
                        Poder.COR_PODER));

                numeroSorteios--;
            }
        }
    }

    /**
     * Remove um tijolo da matriz de tijolos.
     * 
     * @param i Linha do tijolo.
     * @param j Coluna do tijolo.
     */
    private void removerTijolo(int i, int j) {
        this.tijolos[i][j] = null;
    }

    /**
     * Preenche a matriz de tijolos de a cordo com o com o número de tijolos do
     * mapa.
     */
    private void preencherMapa() {
        int indiceTijolo = 0;
        this.tijolos = new Tijolo[numeroLinhas][numeroColunas];

        for (int i = 0; i < numeroLinhas; i++) {
            for (int j = 0; j < numeroColunas; j++) {
                adicionarTijolo(i, j, indiceTijolo);
                indiceTijolo++;
            }
        }
        sorteiaPoderesNosTijolos();
    }

    /**
     * ADiciona um tijolo a matriz de tijolos
     * 
     * @param i            Linha do tijolo.
     * @param j            Coluna do tijolo.
     * @param indiceTijolo Número de sequência do tijolo que vai ser desenhado.
     */
    private void adicionarTijolo(int i, int j, int indiceTijolo) {

        int modelo = tipoTijolos.get(indiceTijolo);

        this.tijolos[i][j] = new Tijolo(
                indiceTijolo,
                modelo,
                fatorX + j * (fatorX),
                fatorY + i * (+fatorY),
                Tijolo.ALTURA_TIJOLO,
                Tijolo.LARGURA_TIJOLO,
                (TipoTijolo.getById(modelo)).cor);
    }

    /**
     * Desenha todos os tijolos do mapa.
     * 
     * @param g2d O objeto gráfico de desenho.
     */
    public void desenharMatriz(Graphics2D g2d) {
        for (int i = 0; i < numeroLinhas; i++) {
            for (int j = 0; j < numeroColunas; j++) {
                if (this.tijolos[i][j] != null) {
                    this.tijolos[i][j].desenhar(g2d);

                    if (this.tijolos[i][j].getPoder() != null) {
                        this.tijolos[i][j].getPoder().desenhar(g2d);
                    }
                }
            }
        }
    }

    public Tijolo getTijolo(int i, int j) {
        return tijolos[i][j];
    }

    public int getNumeroLinhas() {
        return this.numeroLinhas;
    }

    public int getNumeroColunas() {
        return this.numeroColunas;
    }

}

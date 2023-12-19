package engtelecom.poo.areas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import engtelecom.poo.JavanoidOO;
import engtelecom.poo.Mapa;
import engtelecom.poo.elementos.Bolinha;
import engtelecom.poo.elementos.Elemento;
import engtelecom.poo.elementos.Plataforma;
import engtelecom.poo.elementos.Poder;
import engtelecom.poo.elementos.Tijolo;
import engtelecom.poo.elementos.TipoPoder;
import engtelecom.poo.elementos.TipoTijolo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * A classe representa a área do jogo.
 * Esta área abrange a configuração do jogo e o desenho dos seus elementos,
 * incluindo o estado de pausa, o som e a condição de término do jogo.
 */
public class AreaJogo extends Area implements KeyListener {

    /**
     * O mapa do jogo.
     */
    private Mapa mapa;

    /**
     * Elementos da área de jogo, bolinha e a plataforma.
     */
    private ArrayList<Elemento> elementos;

    /**
     * Verdadeiro de o jogo estiver pausado e Falso caso contrário.
     */
    private boolean pausado;

    /**
     * Verdadeiro se o som estiver ligado e Falso caso contrário.
     */
    private boolean somLigado;

    /**
     * Verdadeiro se o jogo terminou e Falseo caso contrário.
     */
    private boolean fimDeJogo;

    public AreaJogo(int coordenadaX, int coordenadaY, int largura, int altura) {
        super(coordenadaX, coordenadaY, largura, altura);
        inicializarElementos();
        inicializarMapa();
        inicializarConfiguracoes();
    }

    /**
     * Inicializa os elementos na área do jogo, a bolinha e a plataforma.
     */
    private void inicializarElementos() {
        elementos = new ArrayList<>();
        elementos.add(new Bolinha());
        elementos.add(new Plataforma());
    }

    /**
     * Inicializa o mapa do jogo com os tijolos e a posição de início da bolinha.
     */
    private void inicializarMapa() {
        mapa = new Mapa(largura, altura);
        Bolinha bolinha = (Bolinha) elementos.get(0);
        bolinha.resetarPosicao();
    }

    /**
     * Configurações do início jogo.
     */
    private void inicializarConfiguracoes() {
        pausado = false;
        somLigado = true;
        fimDeJogo = false;
    }

    /**
     * Desenha a área do jogo.
     *
     * @param g2d Objeto gráfico de desenho.
     */
    @Override
    public void desenharArea(Graphics2D g2d) {
        this.imagem = carregarImagem("imagens/area-jogo.png");
        g2d.drawImage(imagem, this.coordenadaX, this.coordenadaY, this.largura, this.altura, null);

        // g2d.setColor(this.cor);
        // g2d.fillRect(this.coordenadaX, this.coordenadaY, this.largura, this.altura);

        if (!this.pausado && !verificaTerminoJogo()) {
            mapa.desenharMatriz(g2d);
            desenharElementos(g2d);
        } else {
            desenharMensagem(g2d);
        }
    }

    /**
     * Verifica se o jogador perdeu todas as vidas.
     *
     * @return Verdadeiro se o jogo chegou ao fim e Falso caso contrário.
     */
    private boolean verificaTerminoJogo() {
        if (AreaPlacar.fatorVida == 0) {
            this.fimDeJogo = true;
            mapa.resetarNivel();
        }
        return fimDeJogo;
    }

    /**
     * Desenha uma mensagem na tela quando o jogo está parado. Existem dois estados
     * possíveis:
     * quando o jogo é finalizado, exibindo a mensagem "game over", e quando o jogo
     * está pausado
     * intencionalmente, exibindo a mensagem "game paused".
     *
     * @param g2d O objeto gráfico de desenho.
     */
    private void desenharMensagem(Graphics2D g2d) {
        // selecionando cor e fonte da mensagem
        g2d.setColor(Color.LIGHT_GRAY);
        var fonte = carregaFonteDoDisco("f1.ttf", 55f);
        g2d.setFont(fonte);

        // selecionando conteúdo da mensagem
        String mensagem;
        if (fimDeJogo) {
            mensagem = "game over";
        } else {
            mensagem = "game paused";
        }

        // desenhando a mensagem
        g2d.drawString(mensagem, JavanoidOO.AREA_JOGO_X + (JavanoidOO.AREA_JOGO_LARGURA / 8),
                JavanoidOO.AREA_JOGO_ALTURA / 2);
    }

    /**
     * Desenha os elementos de jogo. Durante o desenho, inicia o movimento da
     * bolinha e verifica colisões.
     *
     * @param g2d O objeto gráfico de desenho.
     */
    private void desenharElementos(Graphics2D g2d) {
        // desenha elementos do jogo
        for (var elemento : elementos) {
            elemento.desenhar(g2d);
        }

        // verifica colisões e movimenta a bolinha
        verificarColisoes();
        movimentarBolinhaEmJogo();
    }

    /**
     * Verifica as colisões de elementos entre si.
     */
    private void verificarColisoes() {
        Bolinha bolinha = (Bolinha) elementos.get(0);
        Plataforma plataforma = (Plataforma) elementos.get(1);

        colisaoBolinhaPlataforma(bolinha, plataforma);
        colisaoPlataformaPoderes(plataforma);
        colisaoBolinhaTijolos(bolinha);
    }

    /**
     * Movimenta a bolinha dentro da área de jogo, limitando seu movimento
     */
    private void movimentarBolinhaEmJogo() {
        Bolinha bolinha = (Bolinha) elementos.get(0);
        bolinha.movimentar();

        // ricochetear bola nas bordas da janela
        if (bolinha.getCoordenadaX() < 0
                || bolinha.getCoordenadaX() + bolinha.getLargura() > this.largura) {
            bolinha.setVelocidadeX(bolinha.getVelocidadeX() * -1);
            // this.reproduzirAudio("sons/bola.wav");
        }
        if (bolinha.getCoordenadaY() < 0
                || bolinha.getCoordenadaY() + bolinha.getAltura() > this.altura) {
            bolinha.setVelocidadeY(bolinha.getVelocidadeY() * -1);
            // this.reproduzirAudio("sons/bola.wav");
        }

        // se a bolinha ultrapassou o limite inferior...
        if (passouBordaJogo(bolinha)) {
            // ...remove a vida do jogador, reseta o posicionamento da bolinha e verifica
            // término de jogo
            AreaPlacar.removerVida();
            bolinha.resetarPosicao();
            verificaTerminoJogo();
        }
    }

    /**
     * Verifica se a bolinha passou o limite inferior de tela de jogo.
     * 
     * @param b A bolinha.
     * @return Verdadeiro se ultrapassou o limite e Falso caso contrário.
     */
    private boolean passouBordaJogo(Bolinha b) {
        return b.getCoordenadaY() + b.getAltura() > this.altura;
    }

    /**
     * Movimenta a plataforma dentro dos limites de área de jogo
     * 
     * @param plataforma A plataforma para movimentação.
     */
    private void movimentarPlataformaEmJogo(Plataforma plataforma) {
        plataforma.movimentar();

        // verifica os limites de tela para plataforma
        if (plataforma.getCoordenadaX() < this.coordenadaX) {
            plataforma.setCoordenadaX(0);
        }
        if (plataforma.getCoordenadaX() + plataforma.getLargura() > this.largura) {
            plataforma.setCoordenadaX(this.largura - plataforma.getLargura());
        }
    }

    /**
     * Processa se há uma colisão da bolinha com um tijolo individualmente.
     * 
     * @param bolinha A bolinha a colidir.
     * @param tijolo  O tijolo a ser atingido.
     * @return Verdadeiro se houve a colisão e Falso caso contrário.
     */
    public boolean processarColisaoBolinhaTijolo(Bolinha bolinha, Tijolo tijolo) {

        if (colisaoBlocoFixo(bolinha, tijolo)) {

            int margem = 5; // para evitar bug de colisão

            // lida com as colisões
            if (bolinha.getCoordenadaX() + bolinha.getLargura() - margem <= tijolo.getCoordenadaX()
                    || bolinha.getCoordenadaX() + margem >= tijolo.getCoordenadaX() + tijolo.getLargura()) {
                // inverte a direção da bolinha no eixo X
                bolinha.setVelocidadeX(bolinha.getVelocidadeX() * -1);
            } else {
                // inverte a direção da bolinha no eixo Y
                bolinha.setVelocidadeY(bolinha.getVelocidadeY() * -1);
            }

            return true;
        }

        return false;
    }

    /**
     * Verifica a colisão da bolinha com cada tijolo do mapa.
     * Atualiza a pontuação atual e avança de nível se todos os tijolos forem
     * destruídos (com exceção dos tijolos indestrutíveis).
     *
     * @param bolinha A bolinha a colidir com tijolos.
     */
    private void colisaoBolinhaTijolos(Bolinha bolinha) {
        boolean todosTijolosDestruidos = true;

        // percorre a matriz de tijolos do mapa
        for (int i = 0; i < mapa.getNumeroLinhas(); i++) {
            for (int j = 0; j < mapa.getNumeroColunas(); j++) {

                Tijolo tijolo = mapa.getTijolo(i, j);
                int valor = tijolo.getModelo().valor;

                // se o tijolo selecionado não estiver quebrado
                if (tijolo.getDurabilidadeAtual() > 0) {

                    // verifica se houve colisão
                    if (processarColisaoBolinhaTijolo(bolinha, tijolo)) {

                        // diminui a durabilidade do tijolo
                        tijolo.diminuirDurabilidadeAtual();

                        // se o tijolo quebrou, adiciona pontuação
                        if (tijolo.getDurabilidadeAtual() == 0) {
                            AreaPlacar.adicionarPontuacao(valor);
                        }

                        // se for um tijolo indestrutível, aumenta a pontuação mesmo que não quebre
                        else if (tijolo.getModelo() == TipoTijolo.INDESTRUTIVEL) {
                            AreaPlacar.adicionarPontuacao(valor);
                        }
                    }

                    // verifica se os tijolos já foram destruídos
                    if (tijolo.getModelo() != TipoTijolo.INDESTRUTIVEL) {
                        todosTijolosDestruidos = false;
                    }
                }
            }
        }

        // se todos os tijolos já foram destruídos, avança de nível
        if (todosTijolosDestruidos) {
            mapa.avancarNivel();
        }
    }

    /**
     * Verifica e trata a colisão entre a bolinha e a plataforma.
     *
     * @param bolinha    A bolinha a colidir.
     * @param plataforma A plataforma a ser atingida.
     * @return Verdadeiro se houve a colisão e Falso caso contrário.
     */
    private boolean colisaoBolinhaPlataforma(Bolinha bolinha, Plataforma plataforma) {
        if (colisaoBlocoFixo(bolinha, plataforma)) {
            // ajuste na posição da bolinha para não entrar na plataforma
            bolinha.setCoordenadaY(plataforma.getCoordenadaY() - bolinha.getAltura());
            bolinha.setVelocidadeY(bolinha.getVelocidadeY() * -1);
            return true;
        }
        return false;
    }

    /**
     * Verifica e trata a colisão entre a plataforma e os poderes que podem cair
     * pelo mapa.
     *
     * @param plataforma A plataforma a ser verificada.
     */
    private void colisaoPlataformaPoderes(Plataforma plataforma) {
        // percorre matriz de tijolos
        for (int i = 0; i < mapa.getNumeroLinhas(); i++) {
            for (int j = 0; j < mapa.getNumeroColunas(); j++) {

                // seleciona um tijolo
                Tijolo tijoloSelecionado = this.mapa.getTijolo(i, j);

                // verifica se o tijolo é válido
                if (tijoloSelecionado != null && tijoloSelecionado.getPoder() != null) {
                    Poder poder = tijoloSelecionado.getPoder();

                    // se a plataforma e o poder colidirem, o poder não pode ser capturado novamente
                    if (colisaoBlocoFixo(plataforma, poder) && !poder.jaFoiCapturado()) {
                        aplicaEfeitoPoder(plataforma, poder);
                        poder.capturar();
                        poder.desligarEstadoDesenho();
                    }
                }
            }
        }
    }

    /**
     * Aplica o efeito do poder capturado pela plataforma.
     *
     * @param plataforma A plataforma que pegou o poder.
     * @param poder      O poder capturado.
     */
    private void aplicaEfeitoPoder(Plataforma plataforma, Poder poder) {
        TipoPoder tipo = poder.getTipoPoder();
        switch (tipo) {
            case MAX_VELOCIDADE:
                plataforma.aumentarTamanho();
                break;

            case MIN_VELOCIDADE:
                plataforma.diminuirTamanho();
                break;
            case VIDA_EXTRA:
                AreaPlacar.adicionarVida();
                break;
        }
    }

    /**
     * Verifica a colisão entre dois elementos.
     * 
     * @param elementoA O primeiro elemento verificado;
     * @param elementoB O segundo elemento verificado
     * @return Verdadeiro caso houver uma colisão e Façso caso contrário
     */
    private boolean colisaoBlocoFixo(Elemento elementoA, Elemento elementoB) {
        Rectangle a = new Rectangle(elementoA.getCoordenadaX(), elementoA.getCoordenadaY(),
                elementoA.getLargura(), elementoA.getAltura());
        Rectangle b = new Rectangle(elementoB.getCoordenadaX(), elementoB.getCoordenadaY(),
                elementoB.getLargura(), elementoB.getAltura());

        // retorna a verificação de colisão dos elementos
        return a.intersects(b);
    }

    /**
     * Quando uma tecla é pressionada, faz a sua ação correspondente.
     * 
     * @param e O "evento" de tecla pressionada.
     */
    public void processaTeclaPressionada(KeyEvent e) {
        Plataforma plataforma = (Plataforma) elementos.get(1);
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                plataforma.setVelocidadeX(10);
                movimentarPlataformaEmJogo(plataforma);
                break;
            case KeyEvent.VK_LEFT:
                plataforma.setVelocidadeX(-10);
                movimentarPlataformaEmJogo(plataforma);
                break;
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_S:
                this.somLigado = !this.somLigado;
                System.out.println("Som ligado: " + this.somLigado);
                break;
            case KeyEvent.VK_SPACE:
                this.pausado = !this.pausado;
                break;
        }
    }

    /**
     * Quando uma tecla é solta, faz a sua ação correspondente.
     * 
     * @param e O "evento" de tecla solta.
     * 
     */
    public void processaTeclaSolta(KeyEvent e) {
        Plataforma plataforma = (Plataforma) elementos.get(1);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_LEFT:
                plataforma.setVelocidadeX(0);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        processaTeclaPressionada(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        processaTeclaSolta(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // não faz nada
    }

}

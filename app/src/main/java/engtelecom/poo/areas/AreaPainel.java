package engtelecom.poo.areas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * A classe representa um painel final de pontuação. Ela pede para o usuário
 * inserir o nome dele caso esteja entre os melhores jogadores e apresenta uma
 * tela final.
 * Caso não esteja entre os melhores, mostra somente a pontuação dos melhores
 * jogadores.
 */
public class AreaPainel extends Area {

    /**
     * Lista das melhores pontuações.
     */
    private ArrayList<Integer> melhoresPontuacoes;

    /**
     * Lista dos nomes dos melhores jogadores.
     */
    private ArrayList<String> melhoresJogadores;

    /**
     * Indica se a atualização do placar está liberada.
     * A atualização é controlada para evitar múltiplas inserções e atualizações
     * desnecessárias.
     */
    private boolean atualizacaoLiberada = true;

    /**
     * Indica se o nome do jogador foi inserido.
     * Evita solicitar o nome do jogador várias vezes durante a atualização do
     * placar.
     */
    private boolean nomeInserido = false;

    /**
     * Indica se o placar final foi desenhado.
     * O placar final é desenhado apenas uma vez no final do jogo.
     */
    private boolean placarFinalDesenhado = false;

    public AreaPainel(int coordenadaX, int coordenadaY, int largura, int altura, Color cor) {
        super(coordenadaX, coordenadaY, largura, altura, cor);
        this.melhoresPontuacoes = new ArrayList<>();
        this.melhoresJogadores = new ArrayList<>();
    }

    /**
     * Desenha uma área de jogo final, incluindo o placar de melhores jogadores.
     * Garante que não haja atualizações de tela durante a inserção do nome do
     * jogador.
     * Se o jogador atingir uma nova melhor pontuação, solicita e registra o nome do
     * jogador.
     * Somente aparece ao usuário caso esteja no final de jogo.
     *
     * @param g2d Objeto gráfico de desenho.
     */
    @Override
    public void desenharArea(Graphics2D g2d) {
        // verifica se o jogo chegou ao final e se a atualização de tela está liberada

        if (AreaPlacar.fatorVida == 0 && atualizacaoLiberada && !nomeInserido && !placarFinalDesenhado) {
            // lê as pontuações dos melhores jogadores do arquivo
            ler("melhoresJogadores.csv");

            // se o jogador atingir uma nova melhor pontuação...
            if (novaMelhorPontuacao()) {
                this.atualizacaoLiberada = false;
                // ... solicita e registra o nome do jogador
                String nomeJogadorAtual = receberNome();
                atualizaMelhoresJogadores(nomeJogadorAtual);
                this.atualizacaoLiberada = true;

                // desenha o placar de melhores jogadores
                if (!placarFinalDesenhado) {
                    desenharMelhoresJogadores(g2d);
                    placarFinalDesenhado = true;
                }

                nomeInserido = true;
            }
            // se não atingiu uma pontuação melhor...
            else {
                this.nomeInserido = true;
                this.atualizacaoLiberada = true;

                // ... somente desenha o placar de melhores jogadores
                if (!placarFinalDesenhado) {
                    desenharMelhoresJogadores(g2d);
                    placarFinalDesenhado = true;
                }
            }
        }

        if (placarFinalDesenhado) {
            desenharMelhoresJogadores(g2d);
        }
    }

    /**
     * Mostra uma caixa de diálogo na tela para o jogador inserir seu nome.
     *
     * @return O nome inserido pelo jogador.
     */

    public String receberNome() {
        String nome = JOptionPane.showInputDialog("Informe seu nome");
        JOptionPane.showMessageDialog(null, nome);
        return nome;
    }

    /**
     * Desenha na tela o nome dos melhores jogadores atualmente.
     * 
     * @param g2d Objeto gráfico de desenho.
     */
    private void desenharMelhoresJogadores(Graphics2D g2d) {

        g2d.setColor(this.cor);
        g2d.fillRect(this.coordenadaX, this.coordenadaY, this.largura, this.altura);

        // configura o texto
        g2d.setColor(Color.BLACK);
        var fonte = carregaFonteDoDisco("f1.ttf", 40f);
        g2d.setFont(fonte);

        int coordenadaY = this.coordenadaY + altura / 2;

        for (int i = 0; i < this.melhoresJogadores.size(); i++) {
            String mensagem = this.melhoresJogadores.get(i) + "  " + this.melhoresPontuacoes.get(i);
            int coordenadaX = this.coordenadaX + 20;
            g2d.drawString(mensagem, coordenadaX, coordenadaY);
            coordenadaY += 50;
        }
    }

    /**
     * Atualiza arquivo com a listagem de melhores jogadores.
     * 
     * @param nome Nome do jogador atual.
     */
    private void atualizaMelhoresJogadores(String nome) {
        String jogadorAtual = nome;
        int pontuacaoAtual = AreaPlacar.fatorPontuacao;

        ArrayList<String> jogadores = new ArrayList<>(this.melhoresJogadores);
        ArrayList<Integer> pontuacoes = new ArrayList<>(this.melhoresPontuacoes);

        // verifica se o jogador atual entra na lista
        for (int i = 0; i < this.melhoresJogadores.size(); i++) {
            if (pontuacaoAtual >= pontuacoes.get(i)) {
                pontuacoes.add(i, pontuacaoAtual);
                jogadores.add(i, jogadorAtual);

                // remove o último elemento se a lista ficar maior que 5
                if (pontuacoes.size() > 5) {
                    pontuacoes.remove(5);
                    jogadores.remove(5);
                }
                break; // já adicionou o novo melhor jogador
            }
        }

        // atualiza a lista de melhores jogadores e pontuações
        this.melhoresJogadores = jogadores;
        this.melhoresPontuacoes = pontuacoes;

        // salva as listas em um arquivo
        salvarMelhoresEmArquivo("melhoresJogadores.csv");
    }

    /**
     * Registra melhores jogadores em um arquivo.
     * 
     * @param nomeArquivo O nome do arquivo.
     */
    private void salvarMelhoresEmArquivo(String nomeArquivo) {
        try (FileWriter fw = new FileWriter(nomeArquivo, false);
                BufferedWriter bw = new BufferedWriter(fw)) {

            for (int i = 0; i < this.melhoresJogadores.size(); i++) {
                bw.write(this.melhoresJogadores.get(i) + "," + this.melhoresPontuacoes.get(i));
                bw.newLine();
            }
        } catch (Exception e) {
            System.err.println("erro ao salvar melhores jogadores: " + e.getMessage());
        }
    }

    /**
     * Verifica se a pontuação atual do jogador entra para a lista das melhores
     * pontuações.
     *
     * @return Verdadeiro se a pontuação é uma das melhores e Falso caso
     *         contrário.
     */
    public boolean novaMelhorPontuacao() {
        boolean resposta = false;
        for (var pontuacao : melhoresPontuacoes) {
            if (AreaPlacar.fatorPontuacao >= pontuacao) {
                resposta = true;
                break;
            }
        }
        return resposta;
    }

    /**
     * Faz leitura dos dados dos melhores jogadores.
     * 
     * @param nomeDoArquivo O nome do arquivo contendo os dados.
     */
    public void ler(String nomeDoArquivo) {
        File arquivo = new File(nomeDoArquivo);

        if (!arquivo.exists()) {
            criarArquivo(nomeDoArquivo);
        }

        try (Scanner linha = new Scanner(arquivo)) {

            // enquanto houver outra linha para ler
            while (linha.hasNextLine()) {
                String s = linha.nextLine();

                String[] campos = s.split(",");
                this.melhoresJogadores.add(campos[0]);
                this.melhoresPontuacoes.add(Integer.parseInt(campos[1]));
            }
        } catch (Exception e) {
            System.err.println("erro ao ler arquivo melhores jogadores: " + e.getMessage());
        }
    }

    /**
     * Cria um arquivo para registrar os melhores jogadores.
     * 
     * @param nomeDoArquivo O nome do arquivo a ser criado.
     */
    private void criarArquivo(String nomeDoArquivo) {

        File arquivo = new File(nomeDoArquivo);

        try (FileWriter fw = new FileWriter(arquivo);
                BufferedWriter bw = new BufferedWriter(fw)) {

            // grava as 5 posições melhores como vazias
            for (int i = 0; i < 5; i++) {
                bw.write("null,0");
                bw.newLine();
            }

        } catch (Exception e) {
            System.err.println("erro ao criar o arquivo: " + e.getMessage());
        }
    }
}
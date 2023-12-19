package engtelecom.poo.elementos;

import java.awt.Color;

/**
 * Enumeração que representa os tipos de poderes.
 */
public enum TipoPoder {
    MAX_VELOCIDADE(1, "imagens/max-velocidade.png"),
    MIN_VELOCIDADE(2, "imagens/min-velocidade.png"),
    VIDA_EXTRA(3, "imagens/vida-poder.png");

    /**
     * Identificador único para cada tipo de poder.
     */
    public final int id;

    /**
     * Caminho associado ao arquivo da imagem de cada tipo de poder.
     */
    public final String caminho;

    TipoPoder(int id, String caminho) {
        this.id = id;
        this.caminho = caminho;
    }

    /**
     * Obtém o tipo de poder com base no identificador fornecido.
     *
     * @param i Identificador do tipo de poder.
     * @return Tipo de poder correspondente ao identificador.
     * @throws IllegalArgumentException Se o identificador de poder for inválido.
     */
    public static TipoPoder getById(int i) {
        for (TipoPoder poder : TipoPoder.values()) {
            if (i == poder.id) {
                return poder;
            }
        }
        throw new IllegalArgumentException("identificador de tijolo inválido");
    }

}
package engtelecom.poo.elementos;

import java.awt.Color;

/**
 * Enumeração que representa os tipos de poderes.
 */
public enum TipoPoder {
    MAX_VELOCIDADE(1, Color.ORANGE),
    MIN_VELOCIDADE(2, Color.YELLOW),
    VIDA_EXTRA(3, Color.GREEN);

    /**
     * Identificador único para cada tipo de poder.
     */
    public final int id;

    /**
     * Cor associada a cada tipo de poder.
     */
    public final Color cor;

    TipoPoder(int id, Color cor) {
        this.id = id;
        this.cor = cor;
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
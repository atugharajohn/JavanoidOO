package engtelecom.poo.elementos;

import java.awt.Color;

/**
 * Enumeração que representa os tipos de tijlos.
 */
public enum TipoTijolo {
    FRACO(1, Color.BLUE, 10, 1),
    FORTE(2, Color.PINK, 20, 2),
    INDESTRUTIVEL(3, Color.GREEN, 30, Integer.MAX_VALUE);

    /**
     * Identificador para cada tipo de tijolo.
     */
    public final int id;

    /**
     * Cor associada a cada tipo de tijolo.
     */
    public final Color cor;

    /**
     * Valor associado a cada tipo de tijolo.
     */
    public final int valor;

    /**
     * Durabilidade total associada a cada tipo de tijolo.
     */
    public final int durabilidadeTotal;

    TipoTijolo(int id, Color cor, int valor, int durabilidadeTotal) {
        this.id = id;
        this.cor = cor;
        this.valor = valor;
        this.durabilidadeTotal = durabilidadeTotal;
    }

    /**
     * Obtém o tipo de tijolo com base no identificador fornecido.
     *
     * @param i Identificador do tipo de tijolo.
     * @return Tipo de tijolo com esse identificador.
     */
    public static TipoTijolo getById(int i) {
        for (TipoTijolo tijolo : TipoTijolo.values()) {
            if (i == tijolo.id) {
                return tijolo;
            }
        }
        throw new IllegalArgumentException("identificador de tijolo inválido");
    }

}

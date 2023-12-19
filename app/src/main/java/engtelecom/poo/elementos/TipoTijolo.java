package engtelecom.poo.elementos;

import java.awt.Color;

/**
 * Enumeração que representa os tipos de tijlos.
 */
public enum TipoTijolo {
    FRACO(1, "imagens/tijolo-azul.png", 10, 1),
    FORTE(2, "imagens/tijolo-roxo.png", 20, 2),
    INDESTRUTIVEL(3, "imagens/tijolo-verde.png", 30,
            Integer.MAX_VALUE);

    /**
     * Identificador para cada tipo de tijolo.
     */
    public final int id;

    /**
     * Caminho associado ao arquivo da imagem de cada tipo de tijolo.
     */
    public final String caminho;

    /**
     * Valor associado a cada tipo de tijolo.
     */
    public final int valor;

    /**
     * Durabilidade total associada a cada tipo de tijolo.
     */
    public final int durabilidadeTotal;

    TipoTijolo(int id, String caminho, int valor, int durabilidadeTotal) {
        this.id = id;
        this.caminho = caminho;
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

package br.com.weblaje.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Carregamento {

    /**
     * Carga permanente
     */
    private final double g;

    /**
     * Revestimento
     */
    private final double g1;

    /**
     * Carga acidental - valor tabelado
     */
    private final double q;

    private final double p;

    public Carregamento(double g, double g1, double q) {
        this.g = g;
        this.g1 = g1;
        this.q = q;
        this.p = g + g1 + q;
    }
}

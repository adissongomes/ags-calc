package br.com.weblaje.model;

import java.math.BigDecimal;

public enum Agregado {

    BASALTICA(1.2),
    GRANITO(1),
    CALCARIO(0.9),
    ARENITO(0.7),
    ;

    /**
     * Unidade MPa
     */
    private BigDecimal coeficiente;

    Agregado(double coeficiente) {
        this.coeficiente = new BigDecimal(coeficiente);
    }

    public BigDecimal getCoeficiente() {
        return coeficiente;
    }
}

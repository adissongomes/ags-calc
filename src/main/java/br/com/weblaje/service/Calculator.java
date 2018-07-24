package br.com.weblaje.service;

import br.com.weblaje.model.Laje;

import java.math.BigDecimal;

public class Calculator {

    private final Laje laje;

    public Calculator(Laje laje) {
        this.laje = laje;
    }

    public void calculateLambda() {
        BigDecimal lambda = laje.getLy()
                .divide(laje.getLx())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        laje.setLambda(lambda);
    }

}

package br.com.weblaje.service;

import br.com.weblaje.model.Laje;

import java.math.BigDecimal;

public abstract class Calculadora {
    protected static final double LAMBDA_C = 1.4;
    protected static final double LAMBDA_S = 1.15;

    protected final Laje laje;

    protected Calculadora(Laje laje) {
        this.laje = laje;
    }

    public static Calculadora init(Laje laje) {

        BigDecimal lambda = new BigDecimal(laje.getLy() / laje.getLx())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        laje.setLambda(lambda);

        if (laje.getLambda().doubleValue() > 2) {
            laje.setClasse(Laje.Classe.DIRECAO_UNICA);
            return new CalculadoraUnicaDirecao(laje);
        } else {
            laje.setClasse(Laje.Classe.DIRECAO_DUPLA);
            return new CalculadoraDuplaDirecao(laje);
        }
    }

    public abstract void calcular();

}

package br.com.weblaje;

import br.com.weblaje.model.Laje;
import br.com.weblaje.service.Calculadora;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        Laje laje = Laje.builder()
                .lx(new BigDecimal(2).setScale(2))
                .ly(new BigDecimal(3).setScale(2))
                .build();

        Calculadora calculadora = new Calculadora(laje);
        calculadora.calculaLambda();

        System.out.println(laje);

    }

}

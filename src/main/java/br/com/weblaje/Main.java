package br.com.weblaje;

import br.com.weblaje.model.Laje;
import br.com.weblaje.service.Calculator;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        Laje laje = Laje.builder()
                .lx(new BigDecimal(2).setScale(2))
                .ly(new BigDecimal(3).setScale(2))
                .build();

        Calculator calculator = new Calculator(laje);
        calculator.calculateLambda();

        System.out.println(laje);

    }

}

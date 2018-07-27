package br.com.weblaje;

import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Limites;
import br.com.weblaje.service.Calculadora;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        Laje laje = Laje.builder()
                .lx(4)
                .ly(5)
                .altura(.1)
                .espessuraConcreto(.02)
                .espessuraArgamassa(.02)
                .espessuraMaterial(.01)
                .limites(Limites.builder()
                        .lx(Limites.Edge.SIMPLES)
                        .ly(Limites.Edge.SIMPLES)
                        .lx1(Limites.Edge.ENGASTADO)
                        .ly1(Limites.Edge.ENGASTADO)
                        .build())
                .build();

        Calculadora calculadora = new Calculadora(laje);
        calculadora.calculaLambda();
        calculadora.calculaCarga();
        calculadora.calculaMomentos();
        calculadora.calculaArmaduraFlexao();

        System.out.println(laje);
        System.out.println(laje.getCarregamento());
        System.out.println(laje.getMomento());

    }

}

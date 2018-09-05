package br.com.weblaje;

import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Limites;
import br.com.weblaje.service.Calculadora;
import br.com.weblaje.table.KMDValues;
import br.com.weblaje.table.MarcusValues;

public class Main {

    public static void main(String[] args) {

        MarcusValues values = MarcusValues.getInstance();
        KMDValues kmdValues = KMDValues.getInstance();

        Laje laje = Laje.builder()
                .lx(4)
                .ly(5)
                .altura(.1)
                .caa(Laje.CAA.II)
                .fck(25)
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

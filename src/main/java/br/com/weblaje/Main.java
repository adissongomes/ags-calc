package br.com.weblaje;

import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Limites;
import br.com.weblaje.model.PesosEspecificos;
import br.com.weblaje.service.Calculadora;
import br.com.weblaje.table.*;

public class Main {

    public static void main(String[] args) {

        MarcusValues.getInstance();
        KMDValues.getInstance();
        AreaAcoValues.getInstance();
        ASMinValues.getInstance();
        FlechaValues.getInstance();

        Laje laje = getLajeDuplaDirecao();

        Calculadora c = Calculadora.init(laje);
        c.calcular();

        System.out.println(laje.getN1());
        System.out.println(laje.getN2());
        System.out.println(laje.getCarregamento());
        System.out.println(laje.getMomento());

        laje = getLajeUnicaDirecao();
        c = Calculadora.init(laje);
        c.calcular();

        System.out.println(laje.getN1());
        System.out.println(laje.getN2());
        System.out.println(laje.getCarregamento());
        System.out.println(laje.getMomento());

    }

    private static Laje getLajeUnicaDirecao() {
        return Laje.builder()
                .lx(3)
                .ly(7)
                .altura(.1)
                .caa(Laje.CAA.II)
                .fck(25)
                .espessuraConcreto(.02)
                .espessuraArgamassa(.02)
                .espessuraMaterial(.01)
                .pesosEspecificos(PesosEspecificos.builder()
                        .concretoArmado(25)
                        .argamassa(21)
                        .material(18)
                        .build())
                .limites(Limites.buildLimites(MarcusValues.MarcusType.TYPE_2))
                .build();
    }

    private static Laje getLajeDuplaDirecao() {
        return Laje.builder()
                .lx(4)
                .ly(5)
                .altura(.1)
                .caa(Laje.CAA.II)
                .fck(25)
                .espessuraConcreto(.02)
                .espessuraArgamassa(.02)
                .espessuraMaterial(.01)
                .pesosEspecificos(PesosEspecificos.builder()
                        .concretoArmado(25)
                        .argamassa(21)
                        .material(18)
                        .build())
                .limites(Limites.buildLimites(MarcusValues.MarcusType.TYPE_3))
                .build();
    }

}

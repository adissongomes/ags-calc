package br.com.weblaje;

import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Limites;
import br.com.weblaje.model.PesosEspecificos;
import br.com.weblaje.service.Calculadora;
import br.com.weblaje.table.ASMinValues;
import br.com.weblaje.table.AreaAcoValues;
import br.com.weblaje.table.KMDValues;
import br.com.weblaje.table.MarcusValues;

public class Main {

    public static void main(String[] args) {

        MarcusValues values = MarcusValues.getInstance();
        KMDValues kmdValues = KMDValues.getInstance();
        AreaAcoValues acoValues = AreaAcoValues.getInstance();
        ASMinValues asMinValues = ASMinValues.getInstance();

        Laje laje = Laje.builder()
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

        Calculadora calculadora = Calculadora.init(laje);
        calculadora.calcular();

        System.out.println(laje);
        System.out.println(laje.getCarregamento());
        System.out.println(laje.getMomento());

    }

}

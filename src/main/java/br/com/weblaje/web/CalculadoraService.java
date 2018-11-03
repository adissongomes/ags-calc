package br.com.weblaje.web;

import br.com.weblaje.model.Aco;
import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Limites;
import br.com.weblaje.model.PesosEspecificos;
import br.com.weblaje.service.Calculadora;
import br.com.weblaje.table.MarcusValues;

import java.util.logging.Logger;

public final class CalculadoraService {

    public Laje calcular(LajeDTO dto) {
        Limites limites = Limites.buildLimites(MarcusValues.MarcusType.valueOf(dto.getContorno()));

        Logger.getGlobal().info("CalculoMBean#calcular");

        Laje laje = Laje.builder()
                .lx(dto.getLx())
                .ly(dto.getLy())
                .fck(dto.getFck())
                .q(dto.getQ())
                .altura(dto.getAltura() / 100)
                .limites(limites)
                .aco(Aco.valueOf(dto.getAco()))
                .caa(Laje.CAA.II.valueOf(dto.getCaa()))
                .espessuraConcreto(dto.getE() / 1000)
                .espessuraArgamassa(dto.getEarg() / 1000)
                .espessuraMaterial(dto.getEmat() / 1000)
                .pesosEspecificos(PesosEspecificos.builder()
                        .concretoArmado(dto.getGConcreto())
                        .argamassa(dto.getGArg())
                        .material(dto.getGMat())
                        .build())
                .alpha(dto.getAlpha())
                .build();
        Calculadora c = Calculadora.init(laje);
        c.calcular();

        return laje;
    }
}

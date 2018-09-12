package br.com.weblaje.model;

import lombok.Builder;
import lombok.Data;

/**
 * Define valores de pesos especificos em kN/m2
 * Tabela A 1.3
 */
@Data
@Builder
public class PesosEspecificos {

    @Builder.Default
    private double argamassa = 21;

    @Builder.Default
    private double material = 18;

    @Builder.Default
    private double concretoArmado = 25;

}

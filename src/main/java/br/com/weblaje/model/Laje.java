package br.com.weblaje.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Laje {

    public static final int DIRECAO_SIMPLES = 1;
    public static final int DIRECAO_DUPLA  = 2;

    final BigDecimal lx;

    final BigDecimal ly;

    /**
     * NBR 6118
     * 7cm, 10cm, 12cm
     */
    final BigDecimal altura;

    final Agregado agregado;

    final BigDecimal espessuraArgamassa;

    /**
     * Espessura do piso
     */
    final BigDecimal espessuraMaterial;

    @Builder.Default
    Limites limites = Limites.builder().build();

    /**
     * Classe de agressividade
     * Assume os seguintes valores segundo NBR 6118
     * I - 2.0
     * II- 2.5
     * III - 3.5
     * IV - 4.5
     */
    double caa;

    BigDecimal lambda;

    /**
     * lambda > 2: ARMADO EM UMA DIRECAO
     * lambda <= 2: ARMADO EM DUAS DIRECOES
     */
    @Builder.Default
    int classe = DIRECAO_SIMPLES;

    @Builder.Default
    Aco aco = Aco.CA50;

    /**
     * Unidade MPa
     */
    int fck = 25;

}

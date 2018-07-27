package br.com.weblaje.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Laje {

    /**
     * Classe da laje
     */
    public static final int DIRECAO_SIMPLES = 1;

    /**
     * Classe da laje
     */
    public static final int DIRECAO_DUPLA  = 2;

    private final double lx;

    private final double ly;

    /**
     * NBR 6118
     * 7cm, 10cm, 12cm
     */
    private final double altura;

    private final Agregado agregado;

    private final double espessuraConcreto;

    private final double espessuraArgamassa;

    /**
     * Espessura do piso
     */
    private final double espessuraMaterial;

    @Builder.Default
    private Limites limites = Limites.builder().build();

    /**
     * Classe de agressividade
     * Assume os seguintes valores segundo NBR 6118
     * I - 2.0
     * II- 2.5
     * III - 3.5
     * IV - 4.5
     */
    private double caa;

    private BigDecimal lambda;

    /**
     * lambda > 2: ARMADO EM UMA DIRECAO
     * lambda <= 2: ARMADO EM DUAS DIRECOES
     */
    @Builder.Default
    private int classe = DIRECAO_SIMPLES;

    @Builder.Default
    private Aco aco = Aco.CA50;

    /**
     * Unidade MPa
     */
    private int fck = 25;

    private Carregamento carregamento;

    private Momento momento;

}

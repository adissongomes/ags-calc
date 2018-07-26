package br.com.weblaje.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Carregamento {

    /**
     * Carga permanente
     */
    double g;

    /**
     * Revestimento
     */
    double g1;

    /**
     * Carga acidental - valor tabelado
     */
    @Builder.Default
    double q = 1.5;

}

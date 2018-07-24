package br.com.weblaje.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Laje {

    final BigDecimal lx;
    final BigDecimal ly;
    @Builder.Default Limites limites = Limites.builder().build();
    BigDecimal lambda;

}

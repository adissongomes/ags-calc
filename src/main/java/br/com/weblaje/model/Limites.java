package br.com.weblaje.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Limites {

    public enum Edge {
        ENGASTADO, LIVRE, SIMPLES;
    }

    @Builder.Default
    Edge lx = Edge.SIMPLES,
    lx1 = Edge.SIMPLES,
    ly = Edge.SIMPLES,
    ly1 = Edge.SIMPLES;

}

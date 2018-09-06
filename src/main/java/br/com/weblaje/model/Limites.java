package br.com.weblaje.model;

import br.com.weblaje.table.MarcusValues.MarcusType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Limites {

    public enum Edge {
        ENGASTADO, LIVRE, SIMPLES;
    }

    @Builder.Default
    private Edge lx = Edge.SIMPLES,
            lx1 = Edge.SIMPLES,
            ly = Edge.SIMPLES,
            ly1 = Edge.SIMPLES;

    private MarcusType type;

    public static Limites buildLimites(MarcusType type) {
        LimitesBuilder builder = Limites.builder().type(type);
        switch (type) {
            case TYPE_2:
                return builder
                        .lx(Edge.ENGASTADO)
                        .build();
            case TYPE_3:
                return builder
                        .lx(Edge.ENGASTADO)
                        .ly(Edge.ENGASTADO)
                        .build();
            case TYPE_4:
                return builder
                        .lx(Edge.ENGASTADO)
                        .lx1(Edge.ENGASTADO)
                        .build();
            case TYPE_5:
                return builder
                        .lx(Edge.ENGASTADO)
                        .lx1(Edge.ENGASTADO)
                        .ly(Edge.ENGASTADO)
                        .build();
            case TYPE_6:
                return builder
                        .lx(Edge.ENGASTADO)
                        .lx1(Edge.ENGASTADO)
                        .ly(Edge.ENGASTADO)
                        .ly1(Edge.ENGASTADO)
                        .build();
            default:
                return builder.build();
        }
    }

}

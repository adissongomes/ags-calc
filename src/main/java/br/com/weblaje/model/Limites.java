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
    Edge lx = Edge.SIMPLES,
            lx1 = Edge.SIMPLES,
            ly = Edge.SIMPLES,
            ly1 = Edge.SIMPLES;

    public MarcusType getType() {
        if (lx == Edge.SIMPLES && ly == Edge.SIMPLES) {

            if (lx1 == Edge.SIMPLES && ly1 == Edge.SIMPLES) {
                return MarcusType.TYPE_1;
            }
            if (lx1 == Edge.SIMPLES && ly1 == Edge.ENGASTADO) {
                return MarcusType.TYPE_2;
            }
            if (lx1 == Edge.ENGASTADO && ly1 == Edge.ENGASTADO) {
                return MarcusType.TYPE_3;
            }

        }

        if (lx == Edge.SIMPLES && ly == Edge.ENGASTADO &&
                lx1 == Edge.SIMPLES && ly1 == Edge.ENGASTADO) {
            return MarcusType.TYPE_4;
        }

        if (lx == Edge.ENGASTADO && ly == Edge.SIMPLES &&
                lx1 == Edge.ENGASTADO && ly1 == Edge.ENGASTADO) {
            return MarcusType.TYPE_5;
        }

        return MarcusType.TYPE_6;
    }

}

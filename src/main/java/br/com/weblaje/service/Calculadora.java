package br.com.weblaje.service;

import br.com.weblaje.model.Carregamento;
import br.com.weblaje.model.Laje;

import java.math.BigDecimal;

public class Calculadora {

    private final Laje laje;

    public Calculadora(Laje laje) {
        this.laje = laje;
    }

    public void calculaLambda() {
        BigDecimal lambda = new BigDecimal(laje.getLy()/laje.getLx())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        laje.setLambda(lambda);
    }

    public void calculaCarga() {

        // valores estaticos serao lidos da tabela peso especifico
        // 25 referece ao valor de concreto armado
        double g = laje.getAltura() * 25;

        // 21 refere-se ao valor de argamassa de cimento e areia
        // 18 refere-se ao valor de lajotas ceramicas
        double g1 = laje.getEspessuraArgamassa() * 21 +
                laje.getEspessuraMaterial() * 18;

        Carregamento carregamento = Carregamento
                .builder()
                .q(1.5) // entender se valor é estatico
                .g(g)
                .g1(g1)
                .build();

        laje.setCarregamento(carregamento);

    }

}

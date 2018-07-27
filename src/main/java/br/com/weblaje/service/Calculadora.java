package br.com.weblaje.service;

import br.com.weblaje.model.Carregamento;
import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Momento;

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

        if (lambda.doubleValue() > 2) {
            laje.setClasse(Laje.DIRECAO_SIMPLES);
        } else {
            laje.setClasse(Laje.DIRECAO_DUPLA);
        }
    }

    /**
     * Calcula carga em KN/m2.
     * Resultado é posto em {@link Laje#setCarregamento(Carregamento)}
     */
    public void calculaCarga() {
        // TODO recuperar valores das tabelas
        // valores estaticos serao lidos da tabela peso especifico
        // 25 referece ao valor de concreto armado
        double g = laje.getAltura() * 25;

        // 21 refere-se ao valor de argamassa de cimento e areia
        // 18 refere-se ao valor de lajotas ceramicas
        double g1 = laje.getEspessuraArgamassa() * 21 +
                laje.getEspessuraMaterial() * 18;

        if (g1 < 1)
            g1 = 1;

        Carregamento carregamento = new Carregamento(g, g1, 1.5);
        laje.setCarregamento(carregamento);
    }

    public void calculaMomentos() {
        // TODO ler de tabelas conforme Classe da laje e suas Bordas
        Momento momento = new Momento();
        double pLx = laje.getCarregamento().getP() * Math.pow(laje.getLx(), 2);
        momento.setMx(0.0393 * pLx);
        momento.setMy(0.0251 * pLx);
        momento.setMex(0.0886 * pLx);
        momento.setMey(0.0568 * pLx);
        laje.setMomento(momento);
    }

    public void calculaArmaduraFlexao() {
        // TODO nao sei o que é Yc
        double d = laje.getAltura() - 0.1/2 - 0.25;
        double fcd = laje.getFck() / 1.4 * 1000;
        double fyd = laje.getAco().getFyk() / 1.15 * 1000;
        double md = 1.4 * laje.getMomento().getMx();
        double kmd = md / (1 * Math.pow(d, 2) * fcd);

        // valor ks buscar na tabela conforme valor kmd
        double ks = 42.16;
        double as = md / (ks * d);
        // nao sei o que fazer daqui :P
    }

}

package br.com.weblaje.service;

import br.com.weblaje.model.Carregamento;
import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Momento;
import br.com.weblaje.table.KMDValues;
import br.com.weblaje.table.MarcusValues;

import java.math.BigDecimal;

public class Calculadora {

    private static final double LAMBDA_C = 1.4;
    private static final double LAMBDA_S = 1.15;

    private final Laje laje;

    public Calculadora(Laje laje) {
        this.laje = laje;
    }

    public void calculaLambda() {

        BigDecimal lambda = new BigDecimal(laje.getLy() / laje.getLx())
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

        MarcusValues.MarcusData marcusData = MarcusValues
                .getInstance()
                .getMarcusData(laje.getLimites().getType(), laje.getLambda().doubleValue());

        momento.setMx(marcusData.getCx() * pLx);
        momento.setMy(marcusData.getCy() * pLx);
        momento.setMex(marcusData.getEx() * pLx);
        momento.setMey(marcusData.getEy() * pLx);
        laje.setMomento(momento);
    }

    public void calculaArmaduraFlexao() {
        // TODO nao sei o que é Yc
        double d = laje.getAltura() - (0.01 / 2) - laje.getCaa().getTamanhoEmMetro();
        double fcd = laje.getFck() / LAMBDA_C * 1000;
        double fyd = laje.getAco().getFyk() / LAMBDA_S * 1000;
        double md = LAMBDA_C * laje.getMomento().getMx();
        double kmd = md / (1 * Math.pow(d, 2) * fcd);
        kmd = Double.valueOf(String.format("%.3f", kmd));

        // valor ks buscar na tabela conforme valor kmd
        double ks = KMDValues.getInstance().getKs(laje.getAco(), kmd); // 42.16
        double as = md / (ks * d);
        // nao sei o que fazer daqui :P
    }

}

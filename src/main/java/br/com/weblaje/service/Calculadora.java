package br.com.weblaje.service;

import br.com.weblaje.model.Armadura;
import br.com.weblaje.model.Carregamento;
import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Momento;
import br.com.weblaje.table.ASMinValues;
import br.com.weblaje.table.AreaAcoValues;
import br.com.weblaje.table.AreaAcoValues.AreaAcoData;
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
            laje.setClasse(Laje.Classe.DIRECAO_UNICA);
        } else {
            laje.setClasse(Laje.Classe.DIRECAO_DUPLA);
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
        double g = laje.getAltura() * laje.getPesosEspecificos().getConcretoArmado();

        // 21 refere-se ao valor de argamassa de cimento e areia
        // 18 refere-se ao valor de lajotas ceramicas
        double g1 = laje.getEspessuraArgamassa() * laje.getPesosEspecificos().getArgamassa() +
                laje.getEspessuraMaterial() * laje.getPesosEspecificos().getMaterial();

        if (g1 < 1)
            g1 = 1;

        Carregamento carregamento = new Carregamento(g, g1, laje.getQ());
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

        double d = laje.getAltura() - (0.01 / 2) - laje.getCaa().getTamanhoEmMetro();
        double fcd = laje.getFck() / LAMBDA_C * 1000; // Yc é valor constante
        double md = LAMBDA_C * laje.getMomento().getMx();
        double kmd = md / (1 * Math.pow(d, 2) * fcd);
        kmd = Double.valueOf(String.format("%.3f", kmd));

        // valor ks buscar na tabela conforme valor kmd
        double ks = KMDValues.getInstance().getKs(laje.getAco(), kmd); // 42.16
        double as = Double.valueOf(String.format("%.3f", md / (ks * d)));
        // altura deve ser em centimetros
        double asMin = ASMinValues.getInstance().getAsMin(laje.getAco(), laje.getFck()) * (laje.getAltura() * 100);
        Armadura armadura = Armadura.builder()
                .d(d)
                .fcd(fcd)
                .md(md)
                .kmd(kmd)
                .ks(ks)
                .as(as)
                .asMin(asMin)
                .build();
        laje.setArmadura(armadura);

        AreaAcoData data = AreaAcoValues.getInstance().getData(armadura.getMaiorAs());
        laje.setDadosAco(data);
    }

}

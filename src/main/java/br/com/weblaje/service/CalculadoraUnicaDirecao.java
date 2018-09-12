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

class CalculadoraUnicaDirecao extends Calculadora {

    public CalculadoraUnicaDirecao(Laje laje) {
        super(laje);
    }

    @Override
    public void calcular() {
        throw new UnsupportedOperationException("Precisa desenvolver");
//        calculaCarga();
//        calculaMomentos();
//        calculaArmaduraFlexao();
    }

    /**
     * Calcula carga em KN/m2.
     * Resultado é posto em {@link Laje#setCarregamento(Carregamento)}
     */
    private void calculaCarga() {
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

    private void calculaMomentos() {
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

    private void calculaArmaduraFlexao() {

        double d = laje.getAltura() - (0.01 / 2) - laje.getCaa().getTamanhoEmMetro();
        double fcd = laje.getFck() / LAMBDA_C * 1000; // Yc é valor constante

        AreaAcoData dataX = calculaArmaduraParaEixo(d, fcd, laje.getMomento().getMx());
        AreaAcoData dataY = calculaArmaduraParaEixo(d, fcd, laje.getMomento().getMy());
        laje.setDadosAcoLx(dataX);
        laje.setDadosAcoLy(dataY);

        int numBarrasX = new BigDecimal(laje.getLx() * 100 / dataX.getEspacamento())
                .setScale(0, BigDecimal.ROUND_UP)
                .intValue();
        int numBarrasY = new BigDecimal(laje.getLy() * 100 / dataY.getEspacamento())
                .setScale(0, BigDecimal.ROUND_UP)
                .intValue();
        int comprimentoBarrasX = (int) (laje.getLx() * 100 + 15);
        int comprimentoBarrasY = (int) (laje.getLy() * 100 + 15);

        String n1 = new StringBuilder().append("N1-")
                .append(numBarrasX)
                .append("d").append(dataX.getDiametro())
                .append("c").append(dataX.getEspacamento())
                .append("-").append(comprimentoBarrasX).toString();

        String n2 = new StringBuilder().append("N2-")
                .append(numBarrasY)
                .append("d").append(dataY.getDiametro())
                .append("c").append(dataY.getEspacamento())
                .append("-").append(comprimentoBarrasY).toString();
        laje.setN1(n1);
        laje.setN2(n2);

    }

    private AreaAcoData calculaArmaduraParaEixo(double d, double fcd, double momento) {

        double md = LAMBDA_C * momento;
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

        return AreaAcoValues.getInstance().getData(armadura.getMaiorAs());
    }

}

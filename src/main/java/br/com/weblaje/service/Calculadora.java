package br.com.weblaje.service;

import br.com.weblaje.model.Armadura;
import br.com.weblaje.model.Carregamento;
import br.com.weblaje.model.Flecha;
import br.com.weblaje.model.Laje;
import br.com.weblaje.table.ASMinValues;
import br.com.weblaje.table.AreaAcoValues;
import br.com.weblaje.table.KMDValues;

import java.math.BigDecimal;

public abstract class Calculadora {
    protected static final double LAMBDA_C = 1.4;
    protected static final double LAMBDA_S = 1.15;

    protected final Laje laje;

    protected Calculadora(Laje laje) {
        this.laje = laje;
    }

    public static Calculadora init(Laje laje) {

        BigDecimal lambda = new BigDecimal(laje.getLy() / laje.getLx())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        laje.setLambda(lambda);

        if (laje.getLambda().doubleValue() > 2) {
            laje.setClasse(Laje.Classe.DIRECAO_UNICA);
            return new CalculadoraUnicaDirecao(laje);
        } else {
            laje.setClasse(Laje.Classe.DIRECAO_DUPLA);
            return new CalculadoraDuplaDirecao(laje);
        }
    }

    public void calcular() {
        calculaCarga();
        calculaMomentos();
        calculaArmaduraFlexao();
        calculaFlechaImediata();
    }

    /**
     * Calcula carga em KN/m2.
     * Resultado é posto em {@link Laje#setCarregamento(Carregamento)}
     */
    protected void calculaCarga() {
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

    protected abstract void calculaMomentos();

    protected AreaAcoValues.AreaAcoData calculaArmaduraLx(double d, double fcd) {
        return calculaArmaduraParaEixo(d, fcd, laje.getMomento().getMx());

    }

    protected AreaAcoValues.AreaAcoData calculaArmaduraLy(double d, double fcd) {
        return calculaArmaduraParaEixo(d, fcd, laje.getMomento().getMy());
    }

    protected void calculaFlechaImediata() {

        Flecha.FlechaBuilder flexaBuilder = Flecha.builder();

        double fckInf = 0.7 * 0.3 * Math.pow(laje.getFck(), 2/3) * 1000;
        double i = 1 * laje.getAltura() / 12;
        double y = laje.getAltura() / 2;
        double mr = (1.5 * fckInf * i) / y;

        flexaBuilder.fckInf(fckInf)
                .i(i)
                .y(y)
                .mr(mr);

        double fi = 0;

    }

    private void calculaArmaduraFlexao() {

        double d = laje.getAltura() - (0.01 / 2) - laje.getCaa().getTamanhoEmMetro();
        double fcd = laje.getFck() / LAMBDA_C * 1000; // Yc é valor constante

        laje.setDadosArmaduraLx(calculaArmaduraLx(d, fcd));
        laje.setDadosArmaduraLy(calculaArmaduraLy(d, fcd));

        int numBarrasX = new BigDecimal(laje.getLy() * 100 / laje.getDadosArmaduraLx().getEspacamento())
                .setScale(0, BigDecimal.ROUND_UP)
                .intValue();
        int comprimentoBarrasX = (int) (laje.getLy() * 100 + 15);

        int numBarrasY = new BigDecimal(laje.getLx() * 100 / laje.getDadosArmaduraLy().getEspacamento())
                .setScale(0, BigDecimal.ROUND_UP)
                .intValue();
        int comprimentoBarrasY = (int) (laje.getLx() * 100 + 15);

        laje.getDadosArmaduraLx().setNumeroBarras(numBarrasX);
        laje.getDadosArmaduraLx().setComprimentoBarras(comprimentoBarrasX);
        laje.getDadosArmaduraLy().setNumeroBarras(numBarrasY);
        laje.getDadosArmaduraLy().setComprimentoBarras(comprimentoBarrasY);

        String n1 = defineNotation("N1", laje.getDadosArmaduraLx());
        String n2 = defineNotation("N2", laje.getDadosArmaduraLy());

        laje.setN1(n1);
        laje.setN2(n2);

    }

    private String defineNotation(String name, AreaAcoValues.AreaAcoData data) {
        String n1 = new StringBuilder().append(name)
                .append("-")
                .append(data.getNumeroBarras())
                .append("d").append(data.getDiametro())
                .append("c").append(data.getEspacamento())
                .append("-").append(data.getComprimentoBarras()).toString();

        return n1;
    }

    private AreaAcoValues.AreaAcoData calculaArmaduraParaEixo(double d, double fcd, double momento) {

        double md = LAMBDA_C * momento;
        double kmd = truncateValue(md / (1 * Math.pow(d, 2) * fcd), 3);

        // valor ks buscar na tabela conforme valor kmd
        double ks = KMDValues.getInstance().getKs(laje.getAco(), kmd); // 42.16
        double as = truncateValue(md / (ks * d), 3);
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

    private double truncateValue(double value, int decimals) {
        if (decimals <= 0) decimals = 2;
        double precision = Math.pow(10, decimals);
        int intValue = (int) (value * precision);
        return intValue / precision;
    }


}

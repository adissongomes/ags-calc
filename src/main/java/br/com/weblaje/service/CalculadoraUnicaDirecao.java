package br.com.weblaje.service;

import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Momento;
import br.com.weblaje.table.AreaAcoValues;
import br.com.weblaje.table.AreaAcoValues.AreaAcoData;

import java.math.BigDecimal;

class CalculadoraUnicaDirecao extends Calculadora {

    public CalculadoraUnicaDirecao(Laje laje) {
        super(laje);
    }

    protected void calculaMomentos() {

        Momento momento = new Momento();
        if (laje.getLy() > laje.getLx() * 2) {
            laje.setBalanco(true);
            momento.setMy((laje.getCarregamento().getP() * Math.pow(laje.getLx(), 2)) / 2);
        } else {
            switch (laje.getLimites().getType()) {
                case TYPE_1: // apoiada
                    momento.setMx((laje.getCarregamento().getP() * Math.pow(laje.getLx(), 2)) / 8);
                    momento.setMy(0);
                    break;
                case TYPE_2: // engastada em Lx
                    momento.setMx((laje.getCarregamento().getP() * Math.pow(laje.getLx(), 2)) / 14.22);
                    momento.setMy((laje.getCarregamento().getP() * Math.pow(laje.getLx(), 2)) / 8);
                    break;
                case TYPE_4: // engastada em Lx1
                    momento.setMx((laje.getCarregamento().getP() * Math.pow(laje.getLx(), 2)) / 24);
                    momento.setMy((laje.getCarregamento().getP() * Math.pow(laje.getLx(), 2)) / 12);
                    break;
            }
        }

        laje.setMomento(momento);

    }

    @Override
    protected AreaAcoData calculaArmaduraLy(double d, double fcd) {
        return AreaAcoData.builder().diametro(5).espacamento(25).build();
    }
}

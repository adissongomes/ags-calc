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

class CalculadoraDuplaDirecao extends Calculadora {

    public CalculadoraDuplaDirecao(Laje laje) {
        super(laje);
    }

    protected void calculaMomentos() {
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

}

package br.com.weblaje.table;

import br.com.weblaje.model.Laje;
import lombok.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlechaValues {

    private static final Logger LOGGER = Logger.getLogger(FlechaValues.class.getSimpleName());

    private static FlechaValues INSTANCE;

    private HashMap<Double, Map<TypeFlecha, Double>> flechaTable = new HashMap<>();

    private FlechaValues() {
        readCvs();
    }

    public Double getAlpha(Laje laje) {
        TypeFlecha type = null;

        switch (laje.getLimites().getType()) {
            case TYPE_1:
                type = TypeFlecha.TYPE_1;
                break;
            case TYPE_2:
                type = TypeFlecha.TYPE_2B;
                break;
            case TYPE_3:
                type = TypeFlecha.TYPE_3;
                break;
            case TYPE_4:
                type = TypeFlecha.TYPE_4B;
                break;
            case TYPE_5:
                type = TypeFlecha.TYPE_5A;
                break;
            case TYPE_6:
                type = TypeFlecha.TYPE_6;
                break;
        }

        return flechaTable.get(laje.getLambda().doubleValue() > 2 ? 2.1 : laje.getLambda().doubleValue())
                .get(type);
    }

    public static FlechaValues getInstance() {
        if (INSTANCE == null) INSTANCE = new FlechaValues();
        return INSTANCE;
    }

    private void readCvs() {
        LOGGER.info("Carregando tabelas de flechas...");

        flechaTable = processFile("/flecha.csv");

        LOGGER.info("Tabelas de flechas carregadas: " + flechaTable.toString());
    }

    private HashMap<Double, Map<TypeFlecha, Double>> processFile(String flechaFile) {
        HashMap<Double, Map<TypeFlecha, Double>> dataMap = new HashMap<>();

        try {
            Scanner s = new Scanner(getClass().getResourceAsStream(flechaFile));

            // skip header
            s.nextLine();
            while (s.hasNext()) {
                Map<TypeFlecha, Double> lineData = new HashMap<>();
                String[] line = s.nextLine().split("\t");

                lineData.put(TypeFlecha.TYPE_1, Double.valueOf(line[1].replaceAll(",", ".")));
                lineData.put(TypeFlecha.TYPE_2B, Double.valueOf(line[3].replaceAll(",", ".")));
                lineData.put(TypeFlecha.TYPE_3, Double.valueOf(line[4].replaceAll(",", ".")));
                lineData.put(TypeFlecha.TYPE_4B, Double.valueOf(line[6].replaceAll(",", ".")));
                lineData.put(TypeFlecha.TYPE_5A, Double.valueOf(line[7].replaceAll(",", ".")));
                lineData.put(TypeFlecha.TYPE_6, Double.valueOf(line[9].replaceAll(",", ".")));;

                dataMap.put(Double.valueOf(line[0].replaceAll(",", ".")), lineData);
            }

        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return dataMap;
    }

    public enum TypeFlecha {
        TYPE_1, TYPE_2A, TYPE_2B,
        TYPE_3, TYPE_4A, TYPE_4B,
        TYPE_5A, TYPE_5B, TYPE_6;
    }

    @Data
    @Builder
    public static class FlechaData {
        private double lambda;

        @Singular
        private Map<TypeFlecha, Double> values;
    }

}

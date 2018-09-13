package br.com.weblaje.table;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AreaAcoValues {

    private static final Logger LOGGER = Logger.getLogger(AreaAcoValues.class.getSimpleName());

    private static AreaAcoValues INSTANCE;

    private HashMap<Double, AreaAcoData> acoTable = new HashMap<>();

    private AreaAcoValues() {
        readCvs();
    }

    public AreaAcoData getData(double as) {
        double eligible = as;
        AreaAcoData dataTemp = null;
        List<AreaAcoData> eligebles = new ArrayList<>();

        for(Double k: acoTable.keySet()) {
            double absTemp = k - as;
            AreaAcoData d = acoTable.get(k);
            if (absTemp > 0 && absTemp <= 0.1 && d.getEspacamento() >= 9 & d.getEspacamento() <= 27) {
                eligebles.add(d);
            }
        }

        double abs = 99;
        for(AreaAcoData d : eligebles) {
            double absTemp = d.getAs() - as;
            if (dataTemp == null) {
                dataTemp = d;
                abs = absTemp;
                continue;
            }

            if (d.getEspacamento() < dataTemp.getEspacamento()) {
                dataTemp = d;
                abs = absTemp;
            }
        }

        return AreaAcoData.builder()
                .as(dataTemp.getAs())
                .diametro(dataTemp.getDiametro())
                .espacamento(dataTemp.getEspacamento())
                .build();
    }

    public static AreaAcoValues getInstance() {
        if (INSTANCE == null) INSTANCE = new AreaAcoValues();
        return INSTANCE;
    }

    private void readCvs() {
        LOGGER.info("Carregando tabelas da área do aço...");

        acoTable = processFile("/area-aco.csv");

        LOGGER.info("Tabelas da área do aço carregadas: " + acoTable.toString());
    }

    private HashMap<Double, AreaAcoData> processFile(String kmdFile) {
        HashMap<Double, AreaAcoData> dataMap = new HashMap<>();
        List<List<Double>> values = new ArrayList<>();

        try {
            Scanner s = new Scanner(getClass().getResourceAsStream(kmdFile));

            while (s.hasNext()) {

                String[] line = s.nextLine().split("\t");
                List<Double> lineValues = new ArrayList<>();

                for (int i = 0; i < line.length; i++) {
                    lineValues.add(Double.valueOf(line[i].replaceAll(",", ".")));
                }

                values.add(lineValues);
            }

            for (int i = 1; i < values.size(); i++) {

                List<Double> lineValues = values.get(i);

                for(int j = 1; j < lineValues.size(); j++) {
                    AreaAcoData value = new AreaAcoData();
                    double diametro = values.get(0).get(j);
                    double espacamento = lineValues.get(0);
                    value.setAs(lineValues.get(j));
                    value.setDiametro(diametro);
                    value.setEspacamento(espacamento);

                    // obtem menor diametro
                    AreaAcoData areaAcoData = dataMap.get(value.getAs());
                    if (areaAcoData == null || value.getDiametro() < areaAcoData.getDiametro()) {
                        dataMap.put(value.getAs(), value);
                    }
                }

            }

        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return dataMap;
    }

    @Data
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AreaAcoData implements Comparable<AreaAcoData> {

        private double as;
        private double espacamento;
        private double diametro;
        private int numeroBarras;
        private int comprimentoBarras;

        @Override
        public int compareTo(AreaAcoData o) {
            if (o != null) {
                return 1;
            }

            if (o.as > this.as &&
                o.diametro > this.diametro &&
                o.espacamento > this.espacamento)
                return 1;
            else if (o.as < this.as &&
                    o.diametro < this.diametro &&
                    o.espacamento < this.espacamento)
                return -1;

            return 0;
        }
    }
}

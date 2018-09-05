package br.com.weblaje.table;

import br.com.weblaje.model.Aco;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KMDValues {

    private static final Logger LOGGER = Logger.getLogger(KMDValues.class.getSimpleName());

    private static KMDValues INSTANCE;

    private HashMap<Double, KMDData> kmdTable = new HashMap<>();

    private KMDValues() {
        readCvs();
    }

    public static KMDValues getInstance() {
        if (INSTANCE == null) INSTANCE = new KMDValues();
        return INSTANCE;
    }

    public double getKs(Aco aco, double kmd) {
        KMDData kmdData = this.kmdTable.get(kmd);
        while (kmdData == null) {
            kmd += 0.001;
            kmdData = this.kmdTable.get(kmd);
        }
        if (aco == Aco.CA50) return kmdData.getCa50();
        if (aco == Aco.CA60) return kmdData.getCa60();
        return 0.0;
    }

    private void readCvs() {
        LOGGER.info("Carregando tabelas de KMD...");

        kmdTable = processFile("/kmd.csv");

        LOGGER.info("Tabelas de KMD carregadas: " + kmdTable.toString());
    }

    private HashMap<Double, KMDData> processFile(String kmdFile) {
        HashMap<Double, KMDData> dataMap = new HashMap<>();

        try {
            Scanner s = new Scanner(getClass().getResourceAsStream(kmdFile));
            String[] header = s.nextLine().split("\t");
            while (s.hasNext()) {
                String[] line = s.nextLine().split("\t");
                KMDData data = new KMDData();
                for (int i = 0; i < header.length; i++) {
                    if (i >= line.length) break;
                    data.set(header[i], line[i]);
                }
                dataMap.put(data.getKmd(), data);
            }
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return dataMap;
    }

    @Data
    public static class KMDData {

        private static Field[] declaredFields;

        static {
            declaredFields = KMDData.class.getDeclaredFields();
            for (Field f : declaredFields) {
                f.setAccessible(true);
            }
        }

        private double kmd;
        private double kx;
        private double kz;
        private double ec;
        private double es;
        private double ca25;
        private double ca50;
        private double ca60;

        public void set(String fieldName, String value) {

            value = value.replaceAll(",", ".");

            try {
                for (int i = 0; i < declaredFields.length; i++) {
                    Field declaredField = declaredFields[i];
                    if (declaredField.getName().equals(fieldName)) {
                        declaredField.setDouble(this, Double.valueOf(value));
                        break;
                    }
                }
            } catch (IllegalAccessException e) {
                LOGGER.warning("Value cannot be defined: " + e.getMessage());
            }

//            switch (fieldName) {
//                case "kmd":
//                    kmd = Double.parseDouble(value);
//                    break;
//                case "kx":
//                    kx = Double.parseDouble(value);
//                    break;
//                case "kz":
//                    kz = Double.parseDouble(value);
//                    break;
//                case "ec":
//                    ec = Double.parseDouble(value);
//                    break;
//                case "es":
//                    es = Double.parseDouble(value);
//                    break;
//                case "ca25":
//                    ca25 = Double.parseDouble(value);
//                    break;
//                case "ca50":
//                    ca50 = Double.parseDouble(value);
//                    break;
//                case "ca60":
//                    ca60 = Double.parseDouble(value);
//            }
        }
    }
}

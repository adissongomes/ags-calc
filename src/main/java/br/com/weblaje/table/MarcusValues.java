package br.com.weblaje.table;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MarcusValues {

    public static final Logger LOGGER = Logger.getLogger(MarcusValues.class.getName());
    private final HashMap<Integer, HashMap<Double, MarcusData>> tables = new HashMap<>();

    private static MarcusValues INSTANCE;

    private MarcusValues() {
        readCvs();
    }

    public static MarcusValues getInstance() {
        if (INSTANCE == null) INSTANCE = new MarcusValues();
        return INSTANCE;
    }

    public MarcusData getMarcusData(MarcusType type, double lambda) {
        return tables.get(type.getType()).get(lambda);
    }

    private void readCvs() {
        LOGGER.info("Carregando tabelas de Marcus...");

        for (int i = 1; i <= 6; i++) {
            HashMap<Double, MarcusData> dataMap = processFile("/marcus/marcus-" + i + ".csv");
            tables.put(i, dataMap);
        }

        LOGGER.info("Tabelas de Marcus carregadas: " + tables.toString());
    }

    private HashMap<Double, MarcusData> processFile(String marcus) {
        HashMap<Double, MarcusData> dataMap = new HashMap<>();

        String key = "";
        try {
            Scanner s = new Scanner(getClass().getResourceAsStream(marcus));
            String[] header = s.nextLine().split("\t");
            while (s.hasNext()) {
                String[] line = s.nextLine().split("\t");
                MarcusData data = new MarcusData();
                for (int i = 1; i < header.length; i++) {
                    data.set(header[i], line[i]);
                }
                key = line[0];
                dataMap.put(Double.valueOf(key.replaceAll(",", ".")), data);
            }
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return dataMap;
    }

    public enum MarcusType {
        TYPE_1(1), TYPE_2(2),
        TYPE_3(3), TYPE_4(4),
        TYPE_5(5), TYPE_6(6);
        int type;

        MarcusType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public static MarcusType get(int type) {
            return Arrays.stream(values()).filter(t -> t.type == type)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Type not found"));
        }
    }

    @Data
    public static class MarcusData {

        private static final Field[] declaredFields;

        static {
            declaredFields = MarcusData.class.getDeclaredFields();
            for (Field f : declaredFields) {
                f.setAccessible(true);
            }
        }

        private double cx;
        private double cy;
        private double ex;
        private double ey;
        private double kx;

        public void set(String fieldName, String value) {

            value = value.replaceAll(",", ".");

            try {
                for (int i = 0; i < declaredFields.length; i++) {
                    Field declaredField = declaredFields[i];
                    if (declaredField.getName().equals(fieldName)) {
                        declaredField.set(this, Double.valueOf(value));
                        break;
                    }
                }
            } catch (IllegalAccessException e) {
                LOGGER.warning("Value cannot be defined: " + e.getMessage());
            }
        }
    }

}
package br.com.weblaje.table;

import br.com.weblaje.model.Aco;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ASMinValues {

    private static final Logger LOGGER = Logger.getLogger(ASMinValues.class.getSimpleName());

    private static ASMinValues INSTANCE;

    private HashMap<Aco, FckAsMinData> asminDataMap = new HashMap<>();

    private ASMinValues() {
        readCvs();
    }

    /**
     *
     * @param aco
     * @param fck
     * @return taxa minima de armadura de flexao em %
     */
    public double getAsMin(Aco aco, Integer fck) {
        return asminDataMap.get(aco).getValues().get(fck);
    }

    public static ASMinValues getInstance() {
        if (INSTANCE == null) INSTANCE = new ASMinValues();
        return INSTANCE;
    }

    private void readCvs() {
        LOGGER.info("Carregando tabelas de as minimos...");

        FckAsMinData ca50Data = processFile("/asmin/ca50.csv");
        FckAsMinData ca60Data = processFile("/asmin/ca60.csv");

        this.asminDataMap.put(Aco.CA50, ca50Data);
        this.asminDataMap.put(Aco.CA60, ca60Data);

        LOGGER.info("Tabelas de as minimos carregadas: " + asminDataMap.toString());
    }

    private FckAsMinData processFile(String file) {
        FckAsMinData data = new FckAsMinData();

        try {
            Scanner s = new Scanner(getClass().getResourceAsStream(file));
            String[] fcks, values;

            fcks = s.nextLine().split("\t");
            values = s.nextLine().split("\t");

            for (int i = 0; i < fcks.length; i++) {
                Integer fck = Integer.valueOf(fcks[i]);
                Double value = Double.valueOf(values[i].replaceAll(",", "."));
                data.getValues().put(fck, value);
            }
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return data;
    }

    @Data
    public static class FckAsMinData {

        /**
         * Valores de as minimo por fck
         */
        private HashMap<Integer, Double> values = new HashMap<>();

    }
}

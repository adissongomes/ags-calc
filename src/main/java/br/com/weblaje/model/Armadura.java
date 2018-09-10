package br.com.weblaje.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Armadura {

    private double d;
    private double fcd;
    private double md;
    private double kmd;
    private double ks;
    private double as;
    private double asMin;

    public double getMaiorAs() {
        return as > asMin ? as : asMin;
    }

}

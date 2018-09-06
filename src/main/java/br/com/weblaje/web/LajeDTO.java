package br.com.weblaje.web;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LajeDTO {

    private double lx = 4;
    private double ly = 5;
    private double altura = 10;
    private String contorno = "TYPE_1";
    private String caa = "II";
    private String aco = "CA50";
    private int fck = 25;
    private double q = 1.5;
    private double gConcreto = 25;
    private double gArg = 21;
    private double gMat = 18;
    private double e = 25;
    private double earg = 21;
    private double emat = 18;

}

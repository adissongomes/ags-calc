package br.com.weblaje.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Flecha {

    private double fckInf;
    private double i;
    private double y;
    private double mr;

}

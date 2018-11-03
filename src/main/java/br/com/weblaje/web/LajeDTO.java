package br.com.weblaje.web;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LajeDTO {

    @NotNull
    private Double lx = 4.0;
    @NotNull
    private Double ly = 5.0;
    @NotNull
    private Double altura = 10.0;
    @NotNull
    private String contorno = "TYPE_3";
    @NotNull
    private String caa = "II";
    @NotNull
    private String aco = "CA50";
    @NotNull
    private Integer fck = 25;
    @NotNull
    private Double q = 1.5;
    @NotNull
    private Double gConcreto = 25.0;
    @NotNull
    private Double gArg = 20.0;
    @NotNull
    private Double gMat = 18.0;
    @NotNull
    private Double e = 25.0;
    @NotNull
    private Double earg = 21.0;
    @NotNull
    private Double emat = 18.0;
    @NotNull
    private Double alpha = 0.9;

}

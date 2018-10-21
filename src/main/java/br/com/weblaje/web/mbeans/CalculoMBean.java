package br.com.weblaje.web.mbeans;

import br.com.weblaje.model.Aco;
import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Limites;
import br.com.weblaje.model.PesosEspecificos;
import br.com.weblaje.service.Calculadora;
import br.com.weblaje.table.MarcusValues.MarcusType;
import br.com.weblaje.web.LajeDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.logging.Logger;

@ManagedBean
@RequestScoped
@Data
public class CalculoMBean {

    private LajeDTO laje;
    private String resultado;

    public CalculoMBean() {
        Logger.getGlobal().info("CalculoMBean ...");
        this.laje = new LajeDTO();
    }

    public void calcular() {
        Limites limites = Limites.buildLimites(MarcusType.valueOf(laje.getContorno()));

        Logger.getGlobal().info("CalculoMBean#calcular");

        Laje l = Laje.builder()
                .lx(laje.getLx())
                .ly(laje.getLy())
                .fck(laje.getFck())
                .q(laje.getQ())
                .altura(laje.getAltura() / 100)
                .limites(limites)
                .aco(Aco.valueOf(laje.getAco()))
                .caa(Laje.CAA.II.valueOf(laje.getCaa()))
                .espessuraConcreto(laje.getE() / 1000)
                .espessuraArgamassa(laje.getEarg() / 1000)
                .espessuraMaterial(laje.getEmat() / 1000)
                .pesosEspecificos(PesosEspecificos.builder()
                        .concretoArmado(laje.getGConcreto())
                        .argamassa(laje.getGArg())
                        .material(laje.getGMat())
                        .build())
                .alpha(laje.getAlpha())
                .build();
        Calculadora c = Calculadora.init(l);
        c.calcular();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        resultado = gson.toJson(l);
    }

}

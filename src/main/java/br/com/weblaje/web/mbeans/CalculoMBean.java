package br.com.weblaje.web.mbeans;

import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Limites;
import br.com.weblaje.table.MarcusValues.MarcusType;
import br.com.weblaje.web.CalculadoraService;
import br.com.weblaje.web.LajeDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private CalculadoraService service;

    public CalculoMBean() {
        Logger.getGlobal().info("CalculoMBean ...");
        this.laje = new LajeDTO();
        service = new CalculadoraService();
    }

    public void calcular() throws JsonProcessingException {
        Limites limites = Limites.buildLimites(MarcusType.valueOf(laje.getContorno()));

        Logger.getGlobal().info("CalculoMBean#calcular");

        Laje l = service.calcular(laje);

        ObjectMapper mapper = new ObjectMapper();
        resultado = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(l);
    }

}

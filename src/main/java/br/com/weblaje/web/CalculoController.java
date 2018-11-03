package br.com.weblaje.web;

import br.com.weblaje.model.Laje;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculation")
public class CalculoController {

    private static CalculadoraService service;

    static {
        service = new CalculadoraService();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Laje calculate(LajeDTO lajeDTO) {
        return service.calcular(lajeDTO);
    }

}

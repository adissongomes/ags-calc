package br.com.weblaje.web;

import br.com.weblaje.model.Aco;
import br.com.weblaje.model.Laje;
import br.com.weblaje.model.Laje.CAA;
import br.com.weblaje.model.Limites;
import br.com.weblaje.service.Calculadora;
import br.com.weblaje.table.MarcusValues;
import br.com.weblaje.table.MarcusValues.MarcusType;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Calcular")
public class CalculadoraServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        double lx = Double.valueOf(req.getParameter("lx"));
        double ly = Double.valueOf(req.getParameter("ly"));
        double altura = (double) Integer.valueOf(req.getParameter("h")) / 100;
        MarcusType contorno = MarcusType.valueOf(req.getParameter("contorno"));
        CAA caa = CAA.valueOf(req.getParameter("caa"));
        Aco aco = Aco.valueOf(req.getParameter("aco"));
        int fck = Integer.valueOf(req.getParameter("fck"));
        double e = (double) Integer.valueOf(req.getParameter("e")) / 1000;
        double earg = (double) Integer.valueOf(req.getParameter("earg")) / 1000;
        double emat = (double) Integer.valueOf(req.getParameter("emat")) / 1000;

        Limites limites = Limites.buildLimites(contorno);

        Laje laje = Laje.builder()
                .lx(lx)
                .ly(ly)
                .fck(fck)
                .altura(altura)
                .limites(limites)
                .aco(aco)
                .caa(caa)
                .espessuraConcreto(e)
                .espessuraArgamassa(earg)
                .espessuraMaterial(emat)
                .build();
        Calculadora c = new Calculadora(laje);

        c.calculaLambda();
        c.calculaCarga();
        c.calculaMomentos();
        c.calculaArmaduraFlexao();

        Gson gson = new Gson();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(laje));

    }
}

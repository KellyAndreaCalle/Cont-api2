package api.calendario_generador.controllers; 

import api.calendario_generador.entities.Calendario;
import api.calendario_generador.services.CalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioController {

    @Autowired
    private CalendarioService service;

    @GetMapping("/generar/{anio}")
    public boolean generar(@PathVariable int anio) {
        return service.generarCalendario(anio);
    }

    @GetMapping("/listar/{anio}")
    public List<Calendario> listar(@PathVariable int anio) {
        return service.listarCalendario(anio);
    }
}
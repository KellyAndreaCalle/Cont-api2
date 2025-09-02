package apicalendario.com.calendario_api.controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apicalendario.com.calendario_api.services.IFestivoService;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioController {

    @Autowired
    private IFestivoService festivoServicio;

    
    @GetMapping("/verificar/{paisId}/{anio}/{mes}/{dia}")
    public String verificarFestivo(@PathVariable int paisId, @PathVariable int anio, @PathVariable int mes, @PathVariable int dia) {
        // Pasamos el paisId al servicio, aunque la lógica actual no lo use,
        // es buena práctica para futuras ampliaciones.
        return festivoServicio.verificarFecha(anio, mes, dia);
    }

    // URL CORREGIDA: .../festivos/{paisId}/{anio}
    @GetMapping("/festivos/{paisId}/{anio}")
    public List<String> obtenerFestivos(@PathVariable int paisId, @PathVariable int anio) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return festivoServicio.obtenerFestivos(anio)
                .stream()
                .map(formatter::format)
                .collect(Collectors.toList());
    }

    // URL CORREGIDA: .../generar/{paisId}/{anio}
    @GetMapping("/generar/{paisId}/{anio}")
    public boolean generar(@PathVariable int paisId, @PathVariable int anio) {
        return festivoServicio.generarCalendario(anio, paisId);
    }

    // URL CORREGIDA: .../listar/{paisId}/{anio}
    @GetMapping("/listar/{paisId}/{anio}")
    public List<String> listar(@PathVariable int paisId, @PathVariable int anio) {
        return festivoServicio.listarCalendario(anio, paisId);
    }
}

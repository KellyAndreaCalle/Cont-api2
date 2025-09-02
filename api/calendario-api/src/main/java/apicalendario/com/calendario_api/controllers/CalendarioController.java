package apicalendario.com.calendario_api.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import apicalendario.com.calendario_api.dtos.FestivoDto;
import apicalendario.com.calendario_api.entities.Calendario; 
import apicalendario.com.calendario_api.services.IFestivoService;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioController {

    @Autowired
    private IFestivoService festivoServicio;

    @GetMapping("/verificar/{paisId}/{anio}/{mes}/{dia}")
    public String verificarFestivo(@PathVariable int paisId, @PathVariable int anio, @PathVariable int mes, @PathVariable int dia) {
        return festivoServicio.verificarFecha(anio, mes, dia);
    }

    @GetMapping("/festivos/{paisId}/{anio}")
    public List<FestivoDto> obtenerFestivos(@PathVariable int paisId, @PathVariable int anio) {
        return festivoServicio.obtenerFestivos(anio);
    }

    @GetMapping("/generar/{paisId}/{anio}")
    public boolean generar(@PathVariable int paisId, @PathVariable int anio) {
        return festivoServicio.generarCalendario(anio, paisId);
    }

   
    @GetMapping("/listar/{paisId}/{anio}")
    public List<Calendario> listar(@PathVariable int paisId, @PathVariable int anio) {
        return festivoServicio.listarCalendario(anio, paisId);
    }
}
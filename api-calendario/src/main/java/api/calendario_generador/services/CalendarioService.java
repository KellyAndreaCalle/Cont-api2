package api.calendario_generador.services;

import api.calendario_generador.dtos.FestivoDto;
import api.calendario_generador.entities.Calendario;
import api.calendario_generador.entities.Tipo;
import api.calendario_generador.repositories.CalendarioRepository;
import api.calendario_generador.repositories.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarioService {

    @Autowired private RestTemplate restTemplate;
    @Autowired private CalendarioRepository calendarioRepo;
    @Autowired private TipoRepository tipoRepo;

    private String traducirDia(DayOfWeek dia) {
        switch (dia) {
            case MONDAY: return "Lunes";
            case TUESDAY: return "Martes";
            case WEDNESDAY: return "Miércoles";
            case THURSDAY: return "Jueves";
            case FRIDAY: return "Viernes";
            case SATURDAY: return "Sábado";
            case SUNDAY: return "Domingo";
            default: return "";
        }
    }

    private List<FestivoDto> obtenerFestivosDesdeApi(int anio) {
        String url = "http://api-festivos:8080/api/calendario/festivos/1/" + anio;
        ResponseEntity<List<FestivoDto>> response = restTemplate.exchange(
            url, HttpMethod.GET, null, new ParameterizedTypeReference<List<FestivoDto>>() {});
        return response.getBody();
    }

    public boolean generarCalendario(int anio) {
        try {
            
            List<Calendario> diasExistentes = listarCalendario(anio);
            if (diasExistentes != null && !diasExistentes.isEmpty()) {
                calendarioRepo.deleteAll(diasExistentes);
            }
           

            Tipo tipoLaboral = tipoRepo.findById(1).orElseThrow();
            Tipo tipoFinDeSemana = tipoRepo.findById(2).orElseThrow();
            Tipo tipoFestivo = tipoRepo.findById(3).orElseThrow();

            List<LocalDate> festivos = obtenerFestivosDesdeApi(anio).stream()
                .map(dto -> LocalDate.parse(dto.getFecha()))
                .collect(Collectors.toList());

            LocalDate fecha = LocalDate.of(anio, 1, 1);
            while (fecha.getYear() == anio) {
                Calendario dia = new Calendario();
                dia.setFecha(Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                dia.setDescripcion(traducirDia(fecha.getDayOfWeek()));

                if (festivos.contains(fecha)) {
                    dia.setTipo(tipoFestivo);
                } else if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    dia.setTipo(tipoFinDeSemana);
                } else {
                    dia.setTipo(tipoLaboral);
                }
                calendarioRepo.save(dia);
                fecha = fecha.plusDays(1);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Calendario> listarCalendario(int anio) {
        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);
        Date fechaInicio = Date.from(inicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(fin.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return calendarioRepo.findByFechaBetween(fechaInicio, fechaFin);
    }
}
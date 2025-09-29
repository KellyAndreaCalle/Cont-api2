package apicalendario.com.calendario_api.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import apicalendario.com.calendario_api.dtos.FestivoDto;
import apicalendario.com.calendario_api.entities.Calendario;
import apicalendario.com.calendario_api.entities.Festivo;
import apicalendario.com.calendario_api.entities.Pais;
import apicalendario.com.calendario_api.entities.Tipo;
import apicalendario.com.calendario_api.repositories.CalendarioRepository;
import apicalendario.com.calendario_api.repositories.FestivoRepository;
import apicalendario.com.calendario_api.repositories.PaisRepository;
import apicalendario.com.calendario_api.repositories.TipoRepository;

@Service
public class FestivoService implements IFestivoService {

    @Autowired private FestivoRepository festivoRepositorio;
    @Autowired private CalendarioRepository calendarioRepositorio;
    @Autowired private PaisRepository paisRepositorio;
    @Autowired private TipoRepository tipoRepositorio;

    
    private LocalDate moverA_Lunes(LocalDate fecha) {
        if (fecha.getDayOfWeek() != DayOfWeek.MONDAY) {
            return fecha.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        return fecha;
    }

    private LocalDate getDomingoPascua(int anio) {
        int a = anio % 19, b = anio % 4, c = anio % 7;
        int d = (19 * a + 24) % 30;
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        LocalDate domingoRamos = LocalDate.of(anio, 3, 15).plusDays(dias);
        return domingoRamos.plusDays(7);
    }

    @Override
    public List<FestivoDto> obtenerFestivos(int anio) {
        List<Festivo> festivosDB = festivoRepositorio.findAll();
        List<FestivoDto> fechasFestivas = new ArrayList<>();
        LocalDate pascua = getDomingoPascua(anio);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (final Festivo festivo : festivosDB) {
            LocalDate fechaFestivo;
            switch (festivo.getTipo().getId()) {
                case 1: fechaFestivo = LocalDate.of(anio, festivo.getMes(), festivo.getDia()); break;
                case 2: fechaFestivo = moverA_Lunes(LocalDate.of(anio, festivo.getMes(), festivo.getDia())); break;
                case 3: fechaFestivo = pascua.plusDays(festivo.getDiasPascua()); break;
                case 4: fechaFestivo = moverA_Lunes(pascua.plusDays(festivo.getDiasPascua())); break;
                default: continue;
            }
            fechasFestivas.add(new FestivoDto(festivo.getNombre(), formatter.format(fechaFestivo)));
        }
        return fechasFestivas;
    }

    @Override
    public String verificarFecha(int anio, int mes, int dia) {
        try {
            LocalDate fechaVerificar = LocalDate.of(anio, mes, dia);
            List<FestivoDto> festivos = obtenerFestivos(anio);
            boolean esFestivo = festivos.stream().anyMatch(f -> f.getFecha().equals(fechaVerificar.toString()));
            if (esFestivo) return "Es Festivo";
            else return "No es festivo";
        } catch (Exception e) {
            return "Fecha No válida";
        }
    }

    
    @Override
    public boolean generarCalendario(int anio, int paisId) {
        try {
            
            Pais pais = paisRepositorio.findById(paisId).orElseThrow(() -> new RuntimeException("País no encontrado"));
            Tipo tipoLaboral = tipoRepositorio.findById(1).orElseThrow(() -> new RuntimeException("Tipo Día Laboral no encontrado"));
            Tipo tipoFinDeSemana = tipoRepositorio.findById(2).orElseThrow(() -> new RuntimeException("Tipo Fin de Semana no encontrado"));
            Tipo tipoFestivo = tipoRepositorio.findById(3).orElseThrow(() -> new RuntimeException("Tipo Día Festivo no encontrado"));

            
            List<LocalDate> festivos = obtenerFestivos(anio).stream()
                                                            .map(dto -> LocalDate.parse(dto.getFecha()))
                                                            .collect(Collectors.toList());

            
            LocalDate fecha = LocalDate.of(anio, 1, 1);
            while (fecha.getYear() == anio) {
                Calendario diaCalendario = new Calendario();
                diaCalendario.setPais(pais);
                diaCalendario.setFecha(Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                diaCalendario.setDescripcion(fecha.getDayOfWeek().name());

                
                if (festivos.contains(fecha)) {
                    diaCalendario.setTipo(tipoFestivo);
                } else if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    diaCalendario.setTipo(tipoFinDeSemana);
                } else {
                    diaCalendario.setTipo(tipoLaboral);
                }

                
                calendarioRepositorio.save(diaCalendario);

                fecha = fecha.plusDays(1); 
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Calendario> listarCalendario(int anio, int paisId) {
        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);

        Date fechaInicio = Date.from(inicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(fin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return calendarioRepositorio.findByPaisIdAndFechaBetween(paisId, fechaInicio, fechaFin);
    }
}
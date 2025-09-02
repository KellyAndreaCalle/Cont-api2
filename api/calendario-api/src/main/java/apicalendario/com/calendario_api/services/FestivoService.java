package apicalendario.com.calendario_api.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import apicalendario.com.calendario_api.entities.Festivo;
import apicalendario.com.calendario_api.repositories.FestivoRepository;

@Service
public class FestivoService implements IFestivoService {

    @Autowired
    private FestivoRepository festivoRepositorio;

    private Date moverA_Lunes(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        if (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            fecha = agregarDias(fecha, 8 - c.get(Calendar.DAY_OF_WEEK));
        }
        return fecha;
    }

    private Date agregarDias(Date fecha, int dias) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.DATE, dias);
        return c.getTime();
    }

    private Date getDomingoRamos(int anio) {
        int a = anio % 19;
        int b = anio % 4;
        int c = anio % 7;
        int d = (19 * a + 24) % 30;
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;

        return agregarDias(new Date(anio - 1900, 2, 15), dias);
    }

    @Override
    public List<Date> obtenerFestivos(int anio) {
        List<Festivo> festivos = festivoRepositorio.findAll();
        if (festivos == null) return null;

        List<Date> fechas = new ArrayList<Date>();
        for (final Festivo festivo : festivos) {
            Date domingoRamos = getDomingoRamos(anio);
            switch (festivo.getTipo().getId()) {
                case 1: // Fijo
                    fechas.add(new Date(anio - 1900, festivo.getMes() - 1, festivo.getDia()));
                    break;
                case 2: // Ley Puente
                    fechas.add(moverA_Lunes(new Date(anio - 1900, festivo.getMes() - 1, festivo.getDia())));
                    break;
                case 3: // Basado en Pascua
                    fechas.add(agregarDias(domingoRamos, festivo.getDiasPascua() + 7));
                    break;
                case 4: // Basado en Pascua y Ley Puente
                    fechas.add(moverA_Lunes(agregarDias(domingoRamos, festivo.getDiasPascua() + 7)));
                    break;
            }
        }
        return fechas;
    }

    @Override
    public String verificarFecha(int anio, int mes, int dia) {
        try {
            Date fecha = new Date(anio-1900, mes-1, dia);
            List<Date> festivos = obtenerFestivos(anio);

            for (Date festivo : festivos) {
                if (festivo.getYear() == fecha.getYear() && festivo.getMonth() == fecha.getMonth() && festivo.getDate() == fecha.getDate()) {
                    return "Es festivo";
                }
            }

            Calendar c = Calendar.getInstance();
            c.setTime(fecha);
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                 return "Es fin de semana";
            }

            return "No es festivo";
        } catch (Exception e) {
            return "Fecha No válida";
        }
    }

    @Override
    public boolean generarCalendario(int anio, int paisId) {
        // TODO: Implementar lógica para generar y guardar el calendario anual
        return true;
    }

    @Override
    public List<String> listarCalendario(int anio, int paisId) {
        // TODO: Implementar lógica para listar el calendario desde la base de datos
        return new ArrayList<>();
    }
}

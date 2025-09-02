package apicalendario.com.calendario_api.services;

import java.util.Date;
import java.util.List;

public interface IFestivoService {
    public List<Date> obtenerFestivos(int anio);
    public String verificarFecha(int anio, int mes, int dia);
    public boolean generarCalendario(int anio, int paisId);
    public List<String> listarCalendario(int anio, int paisId);
}

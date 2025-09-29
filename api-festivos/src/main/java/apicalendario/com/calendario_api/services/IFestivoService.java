package apicalendario.com.calendario_api.services;

import java.util.List;
import apicalendario.com.calendario_api.dtos.FestivoDto;
import apicalendario.com.calendario_api.entities.Calendario; 

public interface IFestivoService {
    public List<FestivoDto> obtenerFestivos(int anio);
    public String verificarFecha(int anio, int mes, int dia);
    public boolean generarCalendario(int anio, int paisId);

   
    public List<Calendario> listarCalendario(int anio, int paisId); 
}
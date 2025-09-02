package apicalendario.com.calendario_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivoDto {
    private String festivo;
    private String fecha;
}
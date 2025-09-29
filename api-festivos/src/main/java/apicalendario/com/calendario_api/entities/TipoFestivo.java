package apicalendario.com.calendario_api.entities;
import jakarta.persistence.*;
import lombok.Data;
@Entity @Table(name = "tipofestivo") @Data
public class TipoFestivo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tipo;
}

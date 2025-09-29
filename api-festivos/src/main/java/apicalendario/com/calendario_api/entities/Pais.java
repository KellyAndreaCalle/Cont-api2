package apicalendario.com.calendario_api.entities;
import jakarta.persistence.*;
import lombok.Data;
@Entity @Table(name = "pais") @Data
public class Pais {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
}

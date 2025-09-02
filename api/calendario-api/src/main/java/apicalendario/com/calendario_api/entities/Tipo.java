package apicalendario.com.calendario_api.entities;
import jakarta.persistence.*;
import lombok.Data;
@Entity @Table(name = "tipo") @Data
public class Tipo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tipo;
}

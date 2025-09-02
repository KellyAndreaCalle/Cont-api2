package apicalendario.com.calendario_api.entities;
import jakarta.persistence.*;
import lombok.Data;
@Entity @Table(name = "festivo") @Data
public class Festivo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private int dia;
    private int mes;
    @Column(name = "diaspascua")
    private int diasPascua;
    @ManyToOne @JoinColumn(name = "idtipo")
    private TipoFestivo tipo;
}

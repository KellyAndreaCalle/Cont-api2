package api.calendario_generador.entities; 

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;

@Entity
@Table(name = "calendario")
@Data
public class Calendario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date fecha;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "idtipo")
    private Tipo tipo;
}
package api.calendario_generador.repositories; 

import api.calendario_generador.entities.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Integer> {
    
    List<Calendario> findByFechaBetween(Date start, Date end);
}
package apicalendario.com.calendario_api.repositories;

import apicalendario.com.calendario_api.entities.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Integer> {
    boolean existsByFechaAndPaisId(Date fecha, int paisId);
    List<Calendario> findByPaisIdAndFechaBetween(int paisId, Date start, Date end);
}

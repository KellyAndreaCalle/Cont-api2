package apicalendario.com.calendario_api.repositories;

import apicalendario.com.calendario_api.entities.Festivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivoRepository extends JpaRepository<Festivo, Integer> {

}

package mr.rimrowad.repository;

import mr.rimrowad.domain.MembreEquipe;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MembreEquipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembreEquipeRepository extends JpaRepository<MembreEquipe, Long> {

}

package mr.rimrowad.repository;

import mr.rimrowad.domain.MembreEquipe;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MembreEquipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembreEquipeRepository extends JpaRepository<MembreEquipe, Long> {
	
	@Query("select me from MembreEquipe me join fetch me.equipe e join fetch e.user u where u.login =?1")
	List<MembreEquipe> findMebreEquipeByUserLogin(String string);


}

package mr.rimrowad.repository;

import mr.rimrowad.domain.Projet;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Projet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

    @Query("select projet from Projet projet where projet.user.login = ?#{principal.username}")
    List<Projet> findByUserIsCurrentUser();

}

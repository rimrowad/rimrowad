package mr.rimrowad.web.rest;


import com.codahale.metrics.annotation.Timed;
import mr.rimrowad.domain.Equipe;

import mr.rimrowad.repository.EquipeRepository;
import mr.rimrowad.security.AuthoritiesConstants;
import mr.rimrowad.security.SecurityUtils;
import mr.rimrowad.web.rest.errors.BadRequestAlertException;
import mr.rimrowad.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
//import mr.rimrowad.security.*;
/**
 * REST controller for managing Equipe.
 */
@RestController
@RequestMapping("/api")
public class EquipeResource {

    private final Logger log = LoggerFactory.getLogger(EquipeResource.class);

    private static final String ENTITY_NAME = "equipe";

    private final EquipeRepository equipeRepository;
    private final mr.rimrowad.service.UserService  userService;

  

    public EquipeResource(EquipeRepository equipeRepository, mr.rimrowad.service.UserService userService) {
		super();
		this.equipeRepository = equipeRepository;
		this.userService = userService;
	}

	/**
     * POST  /equipes : Create a new equipe.
     *
     * @param equipe the equipe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new equipe, or with status 400 (Bad Request) if the equipe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/equipes")
    @Timed
    public ResponseEntity<Equipe> createEquipe(@RequestBody Equipe equipe) throws URISyntaxException {
        log.debug("REST request to save Equipe : {}", equipe);
        if (equipe.getId() != null) {
            throw new BadRequestAlertException("A new equipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        equipe.setUser(userService.getUserWithAuthorities().get());
        Equipe result = equipeRepository.save(equipe);
        return ResponseEntity.created(new URI("/api/equipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /equipes : Updates an existing equipe.
     *
     * @param equipe the equipe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated equipe,
     * or with status 400 (Bad Request) if the equipe is not valid,
     * or with status 500 (Internal Server Error) if the equipe couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/equipes")
    @Timed
    public ResponseEntity<Equipe> updateEquipe(@RequestBody Equipe equipe) throws URISyntaxException {
        log.debug("REST request to update Equipe : {}", equipe);
        if (equipe.getId() == null) {
            return createEquipe(equipe);
        }
        Equipe result = equipeRepository.save(equipe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, equipe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /equipes : get all the equipes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of equipes in body
     */
    @GetMapping("/equipes")
    @Timed
    public List<Equipe> getAllEquipes() {
        log.debug("REST request to get all Equipes");
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) 
        return equipeRepository.findAll();
        else
        	return equipeRepository.findAllByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        
        			 			
        }

    /**
     * GET  /equipes/:id : get the "id" equipe.
     *
     * @param id the id of the equipe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the equipe, or with status 404 (Not Found)
     */
    @GetMapping("/equipes/{id}")
    @Timed
    public ResponseEntity<Equipe> getEquipe(@PathVariable Long id) {
        log.debug("REST request to get Equipe : {}", id);
        Equipe equipe = equipeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(equipe));
    }

    /**
     * DELETE  /equipes/:id : delete the "id" equipe.
     *
     * @param id the id of the equipe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/equipes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEquipe(@PathVariable Long id) {
        log.debug("REST request to delete Equipe : {}", id);
        equipeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

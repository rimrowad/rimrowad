package mr.rimrowad.web.rest;

import com.codahale.metrics.annotation.Timed;
import mr.rimrowad.domain.MembreEquipe;

import mr.rimrowad.repository.MembreEquipeRepository;
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

/**
 * REST controller for managing MembreEquipe.
 */
@RestController
@RequestMapping("/api")
public class MembreEquipeResource {

    private final Logger log = LoggerFactory.getLogger(MembreEquipeResource.class);

    private static final String ENTITY_NAME = "membreEquipe";

    private final MembreEquipeRepository membreEquipeRepository;

    public MembreEquipeResource(MembreEquipeRepository membreEquipeRepository) {
        this.membreEquipeRepository = membreEquipeRepository;
    }

    /**
     * POST  /membre-equipes : Create a new membreEquipe.
     *
     * @param membreEquipe the membreEquipe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new membreEquipe, or with status 400 (Bad Request) if the membreEquipe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/membre-equipes")
    @Timed
    public ResponseEntity<MembreEquipe> createMembreEquipe(@RequestBody MembreEquipe membreEquipe) throws URISyntaxException {
        log.debug("REST request to save MembreEquipe : {}", membreEquipe);
        if (membreEquipe.getId() != null) {
            throw new BadRequestAlertException("A new membreEquipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembreEquipe result = membreEquipeRepository.save(membreEquipe);
        return ResponseEntity.created(new URI("/api/membre-equipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /membre-equipes : Updates an existing membreEquipe.
     *
     * @param membreEquipe the membreEquipe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated membreEquipe,
     * or with status 400 (Bad Request) if the membreEquipe is not valid,
     * or with status 500 (Internal Server Error) if the membreEquipe couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/membre-equipes")
    @Timed
    public ResponseEntity<MembreEquipe> updateMembreEquipe(@RequestBody MembreEquipe membreEquipe) throws URISyntaxException {
        log.debug("REST request to update MembreEquipe : {}", membreEquipe);
        if (membreEquipe.getId() == null) {
            return createMembreEquipe(membreEquipe);
        }
        MembreEquipe result = membreEquipeRepository.save(membreEquipe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, membreEquipe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /membre-equipes : get all the membreEquipes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of membreEquipes in body
     */
    @GetMapping("/membre-equipes")
    @Timed
    public List<MembreEquipe> getAllMembreEquipes() {
        log.debug("REST request to get all MembreEquipes");
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) 
        	return membreEquipeRepository.findAll();
            else
            	return membreEquipeRepository.findMebreEquipeByUserLogin(SecurityUtils.getCurrentUserLogin().get());
            
        }

    /**
     * GET  /membre-equipes/:id : get the "id" membreEquipe.
     *
     * @param id the id of the membreEquipe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membreEquipe, or with status 404 (Not Found)
     */
    @GetMapping("/membre-equipes/{id}")
    @Timed
    public ResponseEntity<MembreEquipe> getMembreEquipe(@PathVariable Long id) {
        log.debug("REST request to get MembreEquipe : {}", id);
        MembreEquipe membreEquipe = membreEquipeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(membreEquipe));
    }

    /**
     * DELETE  /membre-equipes/:id : delete the "id" membreEquipe.
     *
     * @param id the id of the membreEquipe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/membre-equipes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMembreEquipe(@PathVariable Long id) {
        log.debug("REST request to delete MembreEquipe : {}", id);
        membreEquipeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package mr.rimrowad.web.rest;

import mr.rimrowad.RimrowadApp;

import mr.rimrowad.domain.MembreEquipe;
import mr.rimrowad.repository.MembreEquipeRepository;
import mr.rimrowad.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static mr.rimrowad.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MembreEquipeResource REST controller.
 *
 * @see MembreEquipeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RimrowadApp.class)
public class MembreEquipeResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOME = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    @Autowired
    private MembreEquipeRepository membreEquipeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMembreEquipeMockMvc;

    private MembreEquipe membreEquipe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MembreEquipeResource membreEquipeResource = new MembreEquipeResource(membreEquipeRepository);
        this.restMembreEquipeMockMvc = MockMvcBuilders.standaloneSetup(membreEquipeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembreEquipe createEntity(EntityManager em) {
        MembreEquipe membreEquipe = new MembreEquipe()
            .nom(DEFAULT_NOM)
            .age(DEFAULT_AGE)
            .diplome(DEFAULT_DIPLOME)
            .experience(DEFAULT_EXPERIENCE);
        return membreEquipe;
    }

    @Before
    public void initTest() {
        membreEquipe = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembreEquipe() throws Exception {
        int databaseSizeBeforeCreate = membreEquipeRepository.findAll().size();

        // Create the MembreEquipe
        restMembreEquipeMockMvc.perform(post("/api/membre-equipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreEquipe)))
            .andExpect(status().isCreated());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeCreate + 1);
        MembreEquipe testMembreEquipe = membreEquipeList.get(membreEquipeList.size() - 1);
        assertThat(testMembreEquipe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMembreEquipe.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testMembreEquipe.getDiplome()).isEqualTo(DEFAULT_DIPLOME);
        assertThat(testMembreEquipe.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
    }

    @Test
    @Transactional
    public void createMembreEquipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membreEquipeRepository.findAll().size();

        // Create the MembreEquipe with an existing ID
        membreEquipe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreEquipeMockMvc.perform(post("/api/membre-equipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreEquipe)))
            .andExpect(status().isBadRequest());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMembreEquipes() throws Exception {
        // Initialize the database
        membreEquipeRepository.saveAndFlush(membreEquipe);

        // Get all the membreEquipeList
        restMembreEquipeMockMvc.perform(get("/api/membre-equipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreEquipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].diplome").value(hasItem(DEFAULT_DIPLOME.toString())))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE.toString())));
    }

    @Test
    @Transactional
    public void getMembreEquipe() throws Exception {
        // Initialize the database
        membreEquipeRepository.saveAndFlush(membreEquipe);

        // Get the membreEquipe
        restMembreEquipeMockMvc.perform(get("/api/membre-equipes/{id}", membreEquipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(membreEquipe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.diplome").value(DEFAULT_DIPLOME.toString()))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMembreEquipe() throws Exception {
        // Get the membreEquipe
        restMembreEquipeMockMvc.perform(get("/api/membre-equipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembreEquipe() throws Exception {
        // Initialize the database
        membreEquipeRepository.saveAndFlush(membreEquipe);
        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();

        // Update the membreEquipe
        MembreEquipe updatedMembreEquipe = membreEquipeRepository.findOne(membreEquipe.getId());
        // Disconnect from session so that the updates on updatedMembreEquipe are not directly saved in db
        em.detach(updatedMembreEquipe);
        updatedMembreEquipe
            .nom(UPDATED_NOM)
            .age(UPDATED_AGE)
            .diplome(UPDATED_DIPLOME)
            .experience(UPDATED_EXPERIENCE);

        restMembreEquipeMockMvc.perform(put("/api/membre-equipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembreEquipe)))
            .andExpect(status().isOk());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate);
        MembreEquipe testMembreEquipe = membreEquipeList.get(membreEquipeList.size() - 1);
        assertThat(testMembreEquipe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMembreEquipe.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testMembreEquipe.getDiplome()).isEqualTo(UPDATED_DIPLOME);
        assertThat(testMembreEquipe.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingMembreEquipe() throws Exception {
        int databaseSizeBeforeUpdate = membreEquipeRepository.findAll().size();

        // Create the MembreEquipe

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMembreEquipeMockMvc.perform(put("/api/membre-equipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreEquipe)))
            .andExpect(status().isCreated());

        // Validate the MembreEquipe in the database
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMembreEquipe() throws Exception {
        // Initialize the database
        membreEquipeRepository.saveAndFlush(membreEquipe);
        int databaseSizeBeforeDelete = membreEquipeRepository.findAll().size();

        // Get the membreEquipe
        restMembreEquipeMockMvc.perform(delete("/api/membre-equipes/{id}", membreEquipe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MembreEquipe> membreEquipeList = membreEquipeRepository.findAll();
        assertThat(membreEquipeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembreEquipe.class);
        MembreEquipe membreEquipe1 = new MembreEquipe();
        membreEquipe1.setId(1L);
        MembreEquipe membreEquipe2 = new MembreEquipe();
        membreEquipe2.setId(membreEquipe1.getId());
        assertThat(membreEquipe1).isEqualTo(membreEquipe2);
        membreEquipe2.setId(2L);
        assertThat(membreEquipe1).isNotEqualTo(membreEquipe2);
        membreEquipe1.setId(null);
        assertThat(membreEquipe1).isNotEqualTo(membreEquipe2);
    }
}

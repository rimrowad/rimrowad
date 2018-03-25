package mr.rimrowad.web.rest;

import mr.rimrowad.RimrowadApp;

import mr.rimrowad.domain.Projet;
import mr.rimrowad.repository.ProjetRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static mr.rimrowad.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import mr.rimrowad.domain.enumeration.ProjetStatus;
import mr.rimrowad.domain.enumeration.Cible;
import mr.rimrowad.domain.enumeration.TypeProjet;
/**
 * Test class for the ProjetResource REST controller.
 *
 * @see ProjetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RimrowadApp.class)
public class ProjetResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ETUDEF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ETUDEF = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ETUDEF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ETUDEF_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ETUDE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ETUDE_2 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ETUDE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ETUDE_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ETUDE_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ETUDE_3 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ETUDE_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ETUDE_3_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_RENDEMENT = 1D;
    private static final Double UPDATED_RENDEMENT = 2D;

    private static final Double DEFAULT_BUDGET = 1D;
    private static final Double UPDATED_BUDGET = 2D;

    private static final Integer DEFAULT_DELAI = 1;
    private static final Integer UPDATED_DELAI = 2;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ProjetStatus DEFAULT_STATUS = ProjetStatus.PENDING;
    private static final ProjetStatus UPDATED_STATUS = ProjetStatus.CANCELED;

    private static final Cible DEFAULT_CIBLE = Cible.HOMME;
    private static final Cible UPDATED_CIBLE = Cible.FEMME;

    private static final TypeProjet DEFAULT_TYPE = TypeProjet.SANTE;
    private static final TypeProjet UPDATED_TYPE = TypeProjet.TRANSPORT;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjetMockMvc;

    private Projet projet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjetResource projetResource = new ProjetResource(projetRepository);
        this.restProjetMockMvc = MockMvcBuilders.standaloneSetup(projetResource)
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
    public static Projet createEntity(EntityManager em) {
        Projet projet = new Projet()
            .title(DEFAULT_TITLE)
            .details(DEFAULT_DETAILS)
            .etudef(DEFAULT_ETUDEF)
            .etudefContentType(DEFAULT_ETUDEF_CONTENT_TYPE)
            .etude2(DEFAULT_ETUDE_2)
            .etude2ContentType(DEFAULT_ETUDE_2_CONTENT_TYPE)
            .etude3(DEFAULT_ETUDE_3)
            .etude3ContentType(DEFAULT_ETUDE_3_CONTENT_TYPE)
            .rendement(DEFAULT_RENDEMENT)
            .budget(DEFAULT_BUDGET)
            .delai(DEFAULT_DELAI)
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS)
            .cible(DEFAULT_CIBLE)
            .type(DEFAULT_TYPE);
        return projet;
    }

    @Before
    public void initTest() {
        projet = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjet() throws Exception {
        int databaseSizeBeforeCreate = projetRepository.findAll().size();

        // Create the Projet
        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isCreated());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate + 1);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProjet.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testProjet.getEtudef()).isEqualTo(DEFAULT_ETUDEF);
        assertThat(testProjet.getEtudefContentType()).isEqualTo(DEFAULT_ETUDEF_CONTENT_TYPE);
        assertThat(testProjet.getEtude2()).isEqualTo(DEFAULT_ETUDE_2);
        assertThat(testProjet.getEtude2ContentType()).isEqualTo(DEFAULT_ETUDE_2_CONTENT_TYPE);
        assertThat(testProjet.getEtude3()).isEqualTo(DEFAULT_ETUDE_3);
        assertThat(testProjet.getEtude3ContentType()).isEqualTo(DEFAULT_ETUDE_3_CONTENT_TYPE);
        assertThat(testProjet.getRendement()).isEqualTo(DEFAULT_RENDEMENT);
        assertThat(testProjet.getBudget()).isEqualTo(DEFAULT_BUDGET);
        assertThat(testProjet.getDelai()).isEqualTo(DEFAULT_DELAI);
        assertThat(testProjet.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testProjet.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProjet.getCible()).isEqualTo(DEFAULT_CIBLE);
        assertThat(testProjet.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createProjetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projetRepository.findAll().size();

        // Create the Projet with an existing ID
        projet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjets() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList
        restProjetMockMvc.perform(get("/api/projets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projet.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].etudefContentType").value(hasItem(DEFAULT_ETUDEF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].etudef").value(hasItem(Base64Utils.encodeToString(DEFAULT_ETUDEF))))
            .andExpect(jsonPath("$.[*].etude2ContentType").value(hasItem(DEFAULT_ETUDE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].etude2").value(hasItem(Base64Utils.encodeToString(DEFAULT_ETUDE_2))))
            .andExpect(jsonPath("$.[*].etude3ContentType").value(hasItem(DEFAULT_ETUDE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].etude3").value(hasItem(Base64Utils.encodeToString(DEFAULT_ETUDE_3))))
            .andExpect(jsonPath("$.[*].rendement").value(hasItem(DEFAULT_RENDEMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].budget").value(hasItem(DEFAULT_BUDGET.doubleValue())))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].cible").value(hasItem(DEFAULT_CIBLE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get the projet
        restProjetMockMvc.perform(get("/api/projets/{id}", projet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projet.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.etudefContentType").value(DEFAULT_ETUDEF_CONTENT_TYPE))
            .andExpect(jsonPath("$.etudef").value(Base64Utils.encodeToString(DEFAULT_ETUDEF)))
            .andExpect(jsonPath("$.etude2ContentType").value(DEFAULT_ETUDE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.etude2").value(Base64Utils.encodeToString(DEFAULT_ETUDE_2)))
            .andExpect(jsonPath("$.etude3ContentType").value(DEFAULT_ETUDE_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.etude3").value(Base64Utils.encodeToString(DEFAULT_ETUDE_3)))
            .andExpect(jsonPath("$.rendement").value(DEFAULT_RENDEMENT.doubleValue()))
            .andExpect(jsonPath("$.budget").value(DEFAULT_BUDGET.doubleValue()))
            .andExpect(jsonPath("$.delai").value(DEFAULT_DELAI))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.cible").value(DEFAULT_CIBLE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjet() throws Exception {
        // Get the projet
        restProjetMockMvc.perform(get("/api/projets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet
        Projet updatedProjet = projetRepository.findOne(projet.getId());
        // Disconnect from session so that the updates on updatedProjet are not directly saved in db
        em.detach(updatedProjet);
        updatedProjet
            .title(UPDATED_TITLE)
            .details(UPDATED_DETAILS)
            .etudef(UPDATED_ETUDEF)
            .etudefContentType(UPDATED_ETUDEF_CONTENT_TYPE)
            .etude2(UPDATED_ETUDE_2)
            .etude2ContentType(UPDATED_ETUDE_2_CONTENT_TYPE)
            .etude3(UPDATED_ETUDE_3)
            .etude3ContentType(UPDATED_ETUDE_3_CONTENT_TYPE)
            .rendement(UPDATED_RENDEMENT)
            .budget(UPDATED_BUDGET)
            .delai(UPDATED_DELAI)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .cible(UPDATED_CIBLE)
            .type(UPDATED_TYPE);

        restProjetMockMvc.perform(put("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjet)))
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProjet.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testProjet.getEtudef()).isEqualTo(UPDATED_ETUDEF);
        assertThat(testProjet.getEtudefContentType()).isEqualTo(UPDATED_ETUDEF_CONTENT_TYPE);
        assertThat(testProjet.getEtude2()).isEqualTo(UPDATED_ETUDE_2);
        assertThat(testProjet.getEtude2ContentType()).isEqualTo(UPDATED_ETUDE_2_CONTENT_TYPE);
        assertThat(testProjet.getEtude3()).isEqualTo(UPDATED_ETUDE_3);
        assertThat(testProjet.getEtude3ContentType()).isEqualTo(UPDATED_ETUDE_3_CONTENT_TYPE);
        assertThat(testProjet.getRendement()).isEqualTo(UPDATED_RENDEMENT);
        assertThat(testProjet.getBudget()).isEqualTo(UPDATED_BUDGET);
        assertThat(testProjet.getDelai()).isEqualTo(UPDATED_DELAI);
        assertThat(testProjet.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProjet.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjet.getCible()).isEqualTo(UPDATED_CIBLE);
        assertThat(testProjet.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Create the Projet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjetMockMvc.perform(put("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isCreated());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);
        int databaseSizeBeforeDelete = projetRepository.findAll().size();

        // Get the projet
        restProjetMockMvc.perform(delete("/api/projets/{id}", projet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projet.class);
        Projet projet1 = new Projet();
        projet1.setId(1L);
        Projet projet2 = new Projet();
        projet2.setId(projet1.getId());
        assertThat(projet1).isEqualTo(projet2);
        projet2.setId(2L);
        assertThat(projet1).isNotEqualTo(projet2);
        projet1.setId(null);
        assertThat(projet1).isNotEqualTo(projet2);
    }
}

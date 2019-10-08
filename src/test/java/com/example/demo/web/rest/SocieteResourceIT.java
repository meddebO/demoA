package com.example.demo.web.rest;

import com.example.demo.DemoAApp;
import com.example.demo.domain.Societe;
import com.example.demo.repository.SocieteRepository;
import com.example.demo.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.demo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SocieteResource} REST controller.
 */
@SpringBootTest(classes = DemoAApp.class)
public class SocieteResourceIT {

    private static final Integer DEFAULT_IDSOCIETE = 1;
    private static final Integer UPDATED_IDSOCIETE = 2;
    private static final Integer SMALLER_IDSOCIETE = 1 - 1;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private SocieteRepository societeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSocieteMockMvc;

    private Societe societe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SocieteResource societeResource = new SocieteResource(societeRepository);
        this.restSocieteMockMvc = MockMvcBuilders.standaloneSetup(societeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societe createEntity(EntityManager em) {
        Societe societe = new Societe()
            .idsociete(DEFAULT_IDSOCIETE)
            .libelle(DEFAULT_LIBELLE)
            .adresse(DEFAULT_ADRESSE);
        return societe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societe createUpdatedEntity(EntityManager em) {
        Societe societe = new Societe()
            .idsociete(UPDATED_IDSOCIETE)
            .libelle(UPDATED_LIBELLE)
            .adresse(UPDATED_ADRESSE);
        return societe;
    }

    @BeforeEach
    public void initTest() {
        societe = createEntity(em);
    }

    @Test
    @Transactional
    public void createSociete() throws Exception {
        int databaseSizeBeforeCreate = societeRepository.findAll().size();

        // Create the Societe
        restSocieteMockMvc.perform(post("/api/societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isCreated());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeCreate + 1);
        Societe testSociete = societeList.get(societeList.size() - 1);
        assertThat(testSociete.getIdsociete()).isEqualTo(DEFAULT_IDSOCIETE);
        assertThat(testSociete.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSociete.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createSocieteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = societeRepository.findAll().size();

        // Create the Societe with an existing ID
        societe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocieteMockMvc.perform(post("/api/societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSocietes() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get all the societeList
        restSocieteMockMvc.perform(get("/api/societes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societe.getId().intValue())))
            .andExpect(jsonPath("$.[*].idsociete").value(hasItem(DEFAULT_IDSOCIETE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }
    
    @Test
    @Transactional
    public void getSociete() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get the societe
        restSocieteMockMvc.perform(get("/api/societes/{id}", societe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(societe.getId().intValue()))
            .andExpect(jsonPath("$.idsociete").value(DEFAULT_IDSOCIETE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSociete() throws Exception {
        // Get the societe
        restSocieteMockMvc.perform(get("/api/societes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSociete() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        int databaseSizeBeforeUpdate = societeRepository.findAll().size();

        // Update the societe
        Societe updatedSociete = societeRepository.findById(societe.getId()).get();
        // Disconnect from session so that the updates on updatedSociete are not directly saved in db
        em.detach(updatedSociete);
        updatedSociete
            .idsociete(UPDATED_IDSOCIETE)
            .libelle(UPDATED_LIBELLE)
            .adresse(UPDATED_ADRESSE);

        restSocieteMockMvc.perform(put("/api/societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSociete)))
            .andExpect(status().isOk());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
        Societe testSociete = societeList.get(societeList.size() - 1);
        assertThat(testSociete.getIdsociete()).isEqualTo(UPDATED_IDSOCIETE);
        assertThat(testSociete.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSociete.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingSociete() throws Exception {
        int databaseSizeBeforeUpdate = societeRepository.findAll().size();

        // Create the Societe

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteMockMvc.perform(put("/api/societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSociete() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        int databaseSizeBeforeDelete = societeRepository.findAll().size();

        // Delete the societe
        restSocieteMockMvc.perform(delete("/api/societes/{id}", societe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Societe.class);
        Societe societe1 = new Societe();
        societe1.setId(1L);
        Societe societe2 = new Societe();
        societe2.setId(societe1.getId());
        assertThat(societe1).isEqualTo(societe2);
        societe2.setId(2L);
        assertThat(societe1).isNotEqualTo(societe2);
        societe1.setId(null);
        assertThat(societe1).isNotEqualTo(societe2);
    }
}

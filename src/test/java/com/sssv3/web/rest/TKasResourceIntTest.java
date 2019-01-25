package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.TKas;
import com.sssv3.repository.TKasRepository;
import com.sssv3.service.TKasService;
import com.sssv3.web.rest.errors.ExceptionTranslator;

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
import java.util.List;


import static com.sssv3.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sssv3.domain.enumeration.KasType;
/**
 * Test class for the TKasResource REST controller.
 *
 * @see TKasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class TKasResourceIntTest {

    private static final KasType DEFAULT_TIPEKAS = KasType.MASUK;
    private static final KasType UPDATED_TIPEKAS = KasType.DELIVERY;

    private static final Double DEFAULT_NOMINAL = 1D;
    private static final Double UPDATED_NOMINAL = 2D;

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Long DEFAULT_OBJECTID = 1L;
    private static final Long UPDATED_OBJECTID = 2L;

    @Autowired
    private TKasRepository tKasRepository;

    @Autowired
    private TKasService tKasService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTKasMockMvc;

    private TKas tKas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TKasResource tKasResource = new TKasResource(tKasService);
        this.restTKasMockMvc = MockMvcBuilders.standaloneSetup(tKasResource)
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
    public static TKas createEntity(EntityManager em) {
        TKas tKas = new TKas()
            .tipekas(DEFAULT_TIPEKAS)
            .nominal(DEFAULT_NOMINAL)
            .deskripsi(DEFAULT_DESKRIPSI)
            .objectid(DEFAULT_OBJECTID);
        return tKas;
    }

    @Before
    public void initTest() {
        tKas = createEntity(em);
    }

    @Test
    @Transactional
    public void createTKas() throws Exception {
        int databaseSizeBeforeCreate = tKasRepository.findAll().size();

        // Create the TKas
        restTKasMockMvc.perform(post("/api/t-kas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tKas)))
            .andExpect(status().isCreated());

        // Validate the TKas in the database
        List<TKas> tKasList = tKasRepository.findAll();
        assertThat(tKasList).hasSize(databaseSizeBeforeCreate + 1);
        TKas testTKas = tKasList.get(tKasList.size() - 1);
        assertThat(testTKas.getTipekas()).isEqualTo(DEFAULT_TIPEKAS);
        assertThat(testTKas.getNominal()).isEqualTo(DEFAULT_NOMINAL);
        assertThat(testTKas.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testTKas.getObjectid()).isEqualTo(DEFAULT_OBJECTID);
    }

    @Test
    @Transactional
    public void createTKasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tKasRepository.findAll().size();

        // Create the TKas with an existing ID
        tKas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTKasMockMvc.perform(post("/api/t-kas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tKas)))
            .andExpect(status().isBadRequest());

        // Validate the TKas in the database
        List<TKas> tKasList = tKasRepository.findAll();
        assertThat(tKasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTKas() throws Exception {
        // Initialize the database
        tKasRepository.saveAndFlush(tKas);

        // Get all the tKasList
        restTKasMockMvc.perform(get("/api/t-kas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tKas.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipekas").value(hasItem(DEFAULT_TIPEKAS.toString())))
            .andExpect(jsonPath("$.[*].nominal").value(hasItem(DEFAULT_NOMINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].objectid").value(hasItem(DEFAULT_OBJECTID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTKas() throws Exception {
        // Initialize the database
        tKasRepository.saveAndFlush(tKas);

        // Get the tKas
        restTKasMockMvc.perform(get("/api/t-kas/{id}", tKas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tKas.getId().intValue()))
            .andExpect(jsonPath("$.tipekas").value(DEFAULT_TIPEKAS.toString()))
            .andExpect(jsonPath("$.nominal").value(DEFAULT_NOMINAL.doubleValue()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.objectid").value(DEFAULT_OBJECTID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTKas() throws Exception {
        // Get the tKas
        restTKasMockMvc.perform(get("/api/t-kas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTKas() throws Exception {
        // Initialize the database
        tKasService.save(tKas);

        int databaseSizeBeforeUpdate = tKasRepository.findAll().size();

        // Update the tKas
        TKas updatedTKas = tKasRepository.findById(tKas.getId()).get();
        // Disconnect from session so that the updates on updatedTKas are not directly saved in db
        em.detach(updatedTKas);
        updatedTKas
            .tipekas(UPDATED_TIPEKAS)
            .nominal(UPDATED_NOMINAL)
            .deskripsi(UPDATED_DESKRIPSI)
            .objectid(UPDATED_OBJECTID);

        restTKasMockMvc.perform(put("/api/t-kas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTKas)))
            .andExpect(status().isOk());

        // Validate the TKas in the database
        List<TKas> tKasList = tKasRepository.findAll();
        assertThat(tKasList).hasSize(databaseSizeBeforeUpdate);
        TKas testTKas = tKasList.get(tKasList.size() - 1);
        assertThat(testTKas.getTipekas()).isEqualTo(UPDATED_TIPEKAS);
        assertThat(testTKas.getNominal()).isEqualTo(UPDATED_NOMINAL);
        assertThat(testTKas.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testTKas.getObjectid()).isEqualTo(UPDATED_OBJECTID);
    }

    @Test
    @Transactional
    public void updateNonExistingTKas() throws Exception {
        int databaseSizeBeforeUpdate = tKasRepository.findAll().size();

        // Create the TKas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTKasMockMvc.perform(put("/api/t-kas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tKas)))
            .andExpect(status().isBadRequest());

        // Validate the TKas in the database
        List<TKas> tKasList = tKasRepository.findAll();
        assertThat(tKasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTKas() throws Exception {
        // Initialize the database
        tKasService.save(tKas);

        int databaseSizeBeforeDelete = tKasRepository.findAll().size();

        // Get the tKas
        restTKasMockMvc.perform(delete("/api/t-kas/{id}", tKas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TKas> tKasList = tKasRepository.findAll();
        assertThat(tKasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TKas.class);
        TKas tKas1 = new TKas();
        tKas1.setId(1L);
        TKas tKas2 = new TKas();
        tKas2.setId(tKas1.getId());
        assertThat(tKas1).isEqualTo(tKas2);
        tKas2.setId(2L);
        assertThat(tKas1).isNotEqualTo(tKas2);
        tKas1.setId(null);
        assertThat(tKas1).isNotEqualTo(tKas2);
    }
}

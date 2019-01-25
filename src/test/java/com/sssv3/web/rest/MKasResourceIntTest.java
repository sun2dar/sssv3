package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MKas;
import com.sssv3.repository.MKasRepository;
import com.sssv3.service.MKasService;
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

import javax.persistence.EntityManager;
import java.util.List;


import static com.sssv3.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MKasResource REST controller.
 *
 * @see MKasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MKasResourceIntTest {

    private static final Double DEFAULT_NOMINAL = 1D;
    private static final Double UPDATED_NOMINAL = 2D;

    @Autowired
    private MKasRepository mKasRepository;

    @Autowired
    private MKasService mKasService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMKasMockMvc;

    private MKas mKas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MKasResource mKasResource = new MKasResource(mKasService);
        this.restMKasMockMvc = MockMvcBuilders.standaloneSetup(mKasResource)
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
    public static MKas createEntity(EntityManager em) {
        MKas mKas = new MKas()
            .nominal(DEFAULT_NOMINAL);
        return mKas;
    }

    @Before
    public void initTest() {
        mKas = createEntity(em);
    }

    @Test
    @Transactional
    public void createMKas() throws Exception {
        int databaseSizeBeforeCreate = mKasRepository.findAll().size();

        // Create the MKas
        restMKasMockMvc.perform(post("/api/m-kas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mKas)))
            .andExpect(status().isCreated());

        // Validate the MKas in the database
        List<MKas> mKasList = mKasRepository.findAll();
        assertThat(mKasList).hasSize(databaseSizeBeforeCreate + 1);
        MKas testMKas = mKasList.get(mKasList.size() - 1);
        assertThat(testMKas.getNominal()).isEqualTo(DEFAULT_NOMINAL);
    }

    @Test
    @Transactional
    public void createMKasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mKasRepository.findAll().size();

        // Create the MKas with an existing ID
        mKas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMKasMockMvc.perform(post("/api/m-kas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mKas)))
            .andExpect(status().isBadRequest());

        // Validate the MKas in the database
        List<MKas> mKasList = mKasRepository.findAll();
        assertThat(mKasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMKas() throws Exception {
        // Initialize the database
        mKasRepository.saveAndFlush(mKas);

        // Get all the mKasList
        restMKasMockMvc.perform(get("/api/m-kas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mKas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nominal").value(hasItem(DEFAULT_NOMINAL.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMKas() throws Exception {
        // Initialize the database
        mKasRepository.saveAndFlush(mKas);

        // Get the mKas
        restMKasMockMvc.perform(get("/api/m-kas/{id}", mKas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mKas.getId().intValue()))
            .andExpect(jsonPath("$.nominal").value(DEFAULT_NOMINAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMKas() throws Exception {
        // Get the mKas
        restMKasMockMvc.perform(get("/api/m-kas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMKas() throws Exception {
        // Initialize the database
        mKasService.save(mKas);

        int databaseSizeBeforeUpdate = mKasRepository.findAll().size();

        // Update the mKas
        MKas updatedMKas = mKasRepository.findById(mKas.getId()).get();
        // Disconnect from session so that the updates on updatedMKas are not directly saved in db
        em.detach(updatedMKas);
        updatedMKas
            .nominal(UPDATED_NOMINAL);

        restMKasMockMvc.perform(put("/api/m-kas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMKas)))
            .andExpect(status().isOk());

        // Validate the MKas in the database
        List<MKas> mKasList = mKasRepository.findAll();
        assertThat(mKasList).hasSize(databaseSizeBeforeUpdate);
        MKas testMKas = mKasList.get(mKasList.size() - 1);
        assertThat(testMKas.getNominal()).isEqualTo(UPDATED_NOMINAL);
    }

    @Test
    @Transactional
    public void updateNonExistingMKas() throws Exception {
        int databaseSizeBeforeUpdate = mKasRepository.findAll().size();

        // Create the MKas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMKasMockMvc.perform(put("/api/m-kas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mKas)))
            .andExpect(status().isBadRequest());

        // Validate the MKas in the database
        List<MKas> mKasList = mKasRepository.findAll();
        assertThat(mKasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMKas() throws Exception {
        // Initialize the database
        mKasService.save(mKas);

        int databaseSizeBeforeDelete = mKasRepository.findAll().size();

        // Get the mKas
        restMKasMockMvc.perform(delete("/api/m-kas/{id}", mKas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MKas> mKasList = mKasRepository.findAll();
        assertThat(mKasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MKas.class);
        MKas mKas1 = new MKas();
        mKas1.setId(1L);
        MKas mKas2 = new MKas();
        mKas2.setId(mKas1.getId());
        assertThat(mKas1).isEqualTo(mKas2);
        mKas2.setId(2L);
        assertThat(mKas1).isNotEqualTo(mKas2);
        mKas1.setId(null);
        assertThat(mKas1).isNotEqualTo(mKas2);
    }
}

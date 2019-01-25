package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MConstant;
import com.sssv3.repository.MConstantRepository;
import com.sssv3.service.MConstantService;
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

/**
 * Test class for the MConstantResource REST controller.
 *
 * @see MConstantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MConstantResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Double DEFAULT_NOMINAL = 1D;
    private static final Double UPDATED_NOMINAL = 2D;

    @Autowired
    private MConstantRepository mConstantRepository;

    @Autowired
    private MConstantService mConstantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMConstantMockMvc;

    private MConstant mConstant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MConstantResource mConstantResource = new MConstantResource(mConstantService);
        this.restMConstantMockMvc = MockMvcBuilders.standaloneSetup(mConstantResource)
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
    public static MConstant createEntity(EntityManager em) {
        MConstant mConstant = new MConstant()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .nominal(DEFAULT_NOMINAL);
        return mConstant;
    }

    @Before
    public void initTest() {
        mConstant = createEntity(em);
    }

    @Test
    @Transactional
    public void createMConstant() throws Exception {
        int databaseSizeBeforeCreate = mConstantRepository.findAll().size();

        // Create the MConstant
        restMConstantMockMvc.perform(post("/api/m-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mConstant)))
            .andExpect(status().isCreated());

        // Validate the MConstant in the database
        List<MConstant> mConstantList = mConstantRepository.findAll();
        assertThat(mConstantList).hasSize(databaseSizeBeforeCreate + 1);
        MConstant testMConstant = mConstantList.get(mConstantList.size() - 1);
        assertThat(testMConstant.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMConstant.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMConstant.getNominal()).isEqualTo(DEFAULT_NOMINAL);
    }

    @Test
    @Transactional
    public void createMConstantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mConstantRepository.findAll().size();

        // Create the MConstant with an existing ID
        mConstant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMConstantMockMvc.perform(post("/api/m-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mConstant)))
            .andExpect(status().isBadRequest());

        // Validate the MConstant in the database
        List<MConstant> mConstantList = mConstantRepository.findAll();
        assertThat(mConstantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mConstantRepository.findAll().size();
        // set the field null
        mConstant.setNama(null);

        // Create the MConstant, which fails.

        restMConstantMockMvc.perform(post("/api/m-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mConstant)))
            .andExpect(status().isBadRequest());

        List<MConstant> mConstantList = mConstantRepository.findAll();
        assertThat(mConstantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNominalIsRequired() throws Exception {
        int databaseSizeBeforeTest = mConstantRepository.findAll().size();
        // set the field null
        mConstant.setNominal(null);

        // Create the MConstant, which fails.

        restMConstantMockMvc.perform(post("/api/m-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mConstant)))
            .andExpect(status().isBadRequest());

        List<MConstant> mConstantList = mConstantRepository.findAll();
        assertThat(mConstantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMConstants() throws Exception {
        // Initialize the database
        mConstantRepository.saveAndFlush(mConstant);

        // Get all the mConstantList
        restMConstantMockMvc.perform(get("/api/m-constants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mConstant.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].nominal").value(hasItem(DEFAULT_NOMINAL.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMConstant() throws Exception {
        // Initialize the database
        mConstantRepository.saveAndFlush(mConstant);

        // Get the mConstant
        restMConstantMockMvc.perform(get("/api/m-constants/{id}", mConstant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mConstant.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.nominal").value(DEFAULT_NOMINAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMConstant() throws Exception {
        // Get the mConstant
        restMConstantMockMvc.perform(get("/api/m-constants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMConstant() throws Exception {
        // Initialize the database
        mConstantService.save(mConstant);

        int databaseSizeBeforeUpdate = mConstantRepository.findAll().size();

        // Update the mConstant
        MConstant updatedMConstant = mConstantRepository.findById(mConstant.getId()).get();
        // Disconnect from session so that the updates on updatedMConstant are not directly saved in db
        em.detach(updatedMConstant);
        updatedMConstant
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .nominal(UPDATED_NOMINAL);

        restMConstantMockMvc.perform(put("/api/m-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMConstant)))
            .andExpect(status().isOk());

        // Validate the MConstant in the database
        List<MConstant> mConstantList = mConstantRepository.findAll();
        assertThat(mConstantList).hasSize(databaseSizeBeforeUpdate);
        MConstant testMConstant = mConstantList.get(mConstantList.size() - 1);
        assertThat(testMConstant.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMConstant.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMConstant.getNominal()).isEqualTo(UPDATED_NOMINAL);
    }

    @Test
    @Transactional
    public void updateNonExistingMConstant() throws Exception {
        int databaseSizeBeforeUpdate = mConstantRepository.findAll().size();

        // Create the MConstant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMConstantMockMvc.perform(put("/api/m-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mConstant)))
            .andExpect(status().isBadRequest());

        // Validate the MConstant in the database
        List<MConstant> mConstantList = mConstantRepository.findAll();
        assertThat(mConstantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMConstant() throws Exception {
        // Initialize the database
        mConstantService.save(mConstant);

        int databaseSizeBeforeDelete = mConstantRepository.findAll().size();

        // Get the mConstant
        restMConstantMockMvc.perform(delete("/api/m-constants/{id}", mConstant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MConstant> mConstantList = mConstantRepository.findAll();
        assertThat(mConstantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MConstant.class);
        MConstant mConstant1 = new MConstant();
        mConstant1.setId(1L);
        MConstant mConstant2 = new MConstant();
        mConstant2.setId(mConstant1.getId());
        assertThat(mConstant1).isEqualTo(mConstant2);
        mConstant2.setId(2L);
        assertThat(mConstant1).isNotEqualTo(mConstant2);
        mConstant1.setId(null);
        assertThat(mConstant1).isNotEqualTo(mConstant2);
    }
}

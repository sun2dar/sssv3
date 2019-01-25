package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MPajak;
import com.sssv3.repository.MPajakRepository;
import com.sssv3.service.MPajakService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.sssv3.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sssv3.domain.enumeration.Status;
/**
 * Test class for the MPajakResource REST controller.
 *
 * @see MPajakResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MPajakResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Double DEFAULT_NOMINAL = 1D;
    private static final Double UPDATED_NOMINAL = 2D;

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MPajakRepository mPajakRepository;

    @Autowired
    private MPajakService mPajakService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMPajakMockMvc;

    private MPajak mPajak;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MPajakResource mPajakResource = new MPajakResource(mPajakService);
        this.restMPajakMockMvc = MockMvcBuilders.standaloneSetup(mPajakResource)
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
    public static MPajak createEntity(EntityManager em) {
        MPajak mPajak = new MPajak()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .nominal(DEFAULT_NOMINAL)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mPajak;
    }

    @Before
    public void initTest() {
        mPajak = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPajak() throws Exception {
        int databaseSizeBeforeCreate = mPajakRepository.findAll().size();

        // Create the MPajak
        restMPajakMockMvc.perform(post("/api/m-pajaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPajak)))
            .andExpect(status().isCreated());

        // Validate the MPajak in the database
        List<MPajak> mPajakList = mPajakRepository.findAll();
        assertThat(mPajakList).hasSize(databaseSizeBeforeCreate + 1);
        MPajak testMPajak = mPajakList.get(mPajakList.size() - 1);
        assertThat(testMPajak.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMPajak.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMPajak.getNominal()).isEqualTo(DEFAULT_NOMINAL);
        assertThat(testMPajak.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMPajak.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMPajakWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPajakRepository.findAll().size();

        // Create the MPajak with an existing ID
        mPajak.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPajakMockMvc.perform(post("/api/m-pajaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPajak)))
            .andExpect(status().isBadRequest());

        // Validate the MPajak in the database
        List<MPajak> mPajakList = mPajakRepository.findAll();
        assertThat(mPajakList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPajakRepository.findAll().size();
        // set the field null
        mPajak.setNama(null);

        // Create the MPajak, which fails.

        restMPajakMockMvc.perform(post("/api/m-pajaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPajak)))
            .andExpect(status().isBadRequest());

        List<MPajak> mPajakList = mPajakRepository.findAll();
        assertThat(mPajakList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMPajaks() throws Exception {
        // Initialize the database
        mPajakRepository.saveAndFlush(mPajak);

        // Get all the mPajakList
        restMPajakMockMvc.perform(get("/api/m-pajaks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPajak.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].nominal").value(hasItem(DEFAULT_NOMINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMPajak() throws Exception {
        // Initialize the database
        mPajakRepository.saveAndFlush(mPajak);

        // Get the mPajak
        restMPajakMockMvc.perform(get("/api/m-pajaks/{id}", mPajak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mPajak.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.nominal").value(DEFAULT_NOMINAL.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMPajak() throws Exception {
        // Get the mPajak
        restMPajakMockMvc.perform(get("/api/m-pajaks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPajak() throws Exception {
        // Initialize the database
        mPajakService.save(mPajak);

        int databaseSizeBeforeUpdate = mPajakRepository.findAll().size();

        // Update the mPajak
        MPajak updatedMPajak = mPajakRepository.findById(mPajak.getId()).get();
        // Disconnect from session so that the updates on updatedMPajak are not directly saved in db
        em.detach(updatedMPajak);
        updatedMPajak
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .nominal(UPDATED_NOMINAL)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMPajakMockMvc.perform(put("/api/m-pajaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMPajak)))
            .andExpect(status().isOk());

        // Validate the MPajak in the database
        List<MPajak> mPajakList = mPajakRepository.findAll();
        assertThat(mPajakList).hasSize(databaseSizeBeforeUpdate);
        MPajak testMPajak = mPajakList.get(mPajakList.size() - 1);
        assertThat(testMPajak.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMPajak.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMPajak.getNominal()).isEqualTo(UPDATED_NOMINAL);
        assertThat(testMPajak.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMPajak.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMPajak() throws Exception {
        int databaseSizeBeforeUpdate = mPajakRepository.findAll().size();

        // Create the MPajak

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPajakMockMvc.perform(put("/api/m-pajaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPajak)))
            .andExpect(status().isBadRequest());

        // Validate the MPajak in the database
        List<MPajak> mPajakList = mPajakRepository.findAll();
        assertThat(mPajakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPajak() throws Exception {
        // Initialize the database
        mPajakService.save(mPajak);

        int databaseSizeBeforeDelete = mPajakRepository.findAll().size();

        // Get the mPajak
        restMPajakMockMvc.perform(delete("/api/m-pajaks/{id}", mPajak.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MPajak> mPajakList = mPajakRepository.findAll();
        assertThat(mPajakList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPajak.class);
        MPajak mPajak1 = new MPajak();
        mPajak1.setId(1L);
        MPajak mPajak2 = new MPajak();
        mPajak2.setId(mPajak1.getId());
        assertThat(mPajak1).isEqualTo(mPajak2);
        mPajak2.setId(2L);
        assertThat(mPajak1).isNotEqualTo(mPajak2);
        mPajak1.setId(null);
        assertThat(mPajak1).isNotEqualTo(mPajak2);
    }
}

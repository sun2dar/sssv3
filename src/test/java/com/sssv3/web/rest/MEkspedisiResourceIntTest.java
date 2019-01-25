package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MEkspedisi;
import com.sssv3.repository.MEkspedisiRepository;
import com.sssv3.service.MEkspedisiService;
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
 * Test class for the MEkspedisiResource REST controller.
 *
 * @see MEkspedisiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MEkspedisiResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final Long DEFAULT_TELEPON = 1L;
    private static final Long UPDATED_TELEPON = 2L;

    private static final Long DEFAULT_MOBILEPHONE = 1L;
    private static final Long UPDATED_MOBILEPHONE = 2L;

    private static final String DEFAULT_ALAMAT = "AAAAAAAAAA";
    private static final String UPDATED_ALAMAT = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MEkspedisiRepository mEkspedisiRepository;

    @Autowired
    private MEkspedisiService mEkspedisiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMEkspedisiMockMvc;

    private MEkspedisi mEkspedisi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MEkspedisiResource mEkspedisiResource = new MEkspedisiResource(mEkspedisiService);
        this.restMEkspedisiMockMvc = MockMvcBuilders.standaloneSetup(mEkspedisiResource)
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
    public static MEkspedisi createEntity(EntityManager em) {
        MEkspedisi mEkspedisi = new MEkspedisi()
            .nama(DEFAULT_NAMA)
            .telepon(DEFAULT_TELEPON)
            .mobilephone(DEFAULT_MOBILEPHONE)
            .alamat(DEFAULT_ALAMAT)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mEkspedisi;
    }

    @Before
    public void initTest() {
        mEkspedisi = createEntity(em);
    }

    @Test
    @Transactional
    public void createMEkspedisi() throws Exception {
        int databaseSizeBeforeCreate = mEkspedisiRepository.findAll().size();

        // Create the MEkspedisi
        restMEkspedisiMockMvc.perform(post("/api/m-ekspedisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mEkspedisi)))
            .andExpect(status().isCreated());

        // Validate the MEkspedisi in the database
        List<MEkspedisi> mEkspedisiList = mEkspedisiRepository.findAll();
        assertThat(mEkspedisiList).hasSize(databaseSizeBeforeCreate + 1);
        MEkspedisi testMEkspedisi = mEkspedisiList.get(mEkspedisiList.size() - 1);
        assertThat(testMEkspedisi.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMEkspedisi.getTelepon()).isEqualTo(DEFAULT_TELEPON);
        assertThat(testMEkspedisi.getMobilephone()).isEqualTo(DEFAULT_MOBILEPHONE);
        assertThat(testMEkspedisi.getAlamat()).isEqualTo(DEFAULT_ALAMAT);
        assertThat(testMEkspedisi.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMEkspedisi.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMEkspedisiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mEkspedisiRepository.findAll().size();

        // Create the MEkspedisi with an existing ID
        mEkspedisi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMEkspedisiMockMvc.perform(post("/api/m-ekspedisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mEkspedisi)))
            .andExpect(status().isBadRequest());

        // Validate the MEkspedisi in the database
        List<MEkspedisi> mEkspedisiList = mEkspedisiRepository.findAll();
        assertThat(mEkspedisiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mEkspedisiRepository.findAll().size();
        // set the field null
        mEkspedisi.setNama(null);

        // Create the MEkspedisi, which fails.

        restMEkspedisiMockMvc.perform(post("/api/m-ekspedisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mEkspedisi)))
            .andExpect(status().isBadRequest());

        List<MEkspedisi> mEkspedisiList = mEkspedisiRepository.findAll();
        assertThat(mEkspedisiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMEkspedisis() throws Exception {
        // Initialize the database
        mEkspedisiRepository.saveAndFlush(mEkspedisi);

        // Get all the mEkspedisiList
        restMEkspedisiMockMvc.perform(get("/api/m-ekspedisis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mEkspedisi.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].telepon").value(hasItem(DEFAULT_TELEPON.intValue())))
            .andExpect(jsonPath("$.[*].mobilephone").value(hasItem(DEFAULT_MOBILEPHONE.intValue())))
            .andExpect(jsonPath("$.[*].alamat").value(hasItem(DEFAULT_ALAMAT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMEkspedisi() throws Exception {
        // Initialize the database
        mEkspedisiRepository.saveAndFlush(mEkspedisi);

        // Get the mEkspedisi
        restMEkspedisiMockMvc.perform(get("/api/m-ekspedisis/{id}", mEkspedisi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mEkspedisi.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.telepon").value(DEFAULT_TELEPON.intValue()))
            .andExpect(jsonPath("$.mobilephone").value(DEFAULT_MOBILEPHONE.intValue()))
            .andExpect(jsonPath("$.alamat").value(DEFAULT_ALAMAT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMEkspedisi() throws Exception {
        // Get the mEkspedisi
        restMEkspedisiMockMvc.perform(get("/api/m-ekspedisis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMEkspedisi() throws Exception {
        // Initialize the database
        mEkspedisiService.save(mEkspedisi);

        int databaseSizeBeforeUpdate = mEkspedisiRepository.findAll().size();

        // Update the mEkspedisi
        MEkspedisi updatedMEkspedisi = mEkspedisiRepository.findById(mEkspedisi.getId()).get();
        // Disconnect from session so that the updates on updatedMEkspedisi are not directly saved in db
        em.detach(updatedMEkspedisi);
        updatedMEkspedisi
            .nama(UPDATED_NAMA)
            .telepon(UPDATED_TELEPON)
            .mobilephone(UPDATED_MOBILEPHONE)
            .alamat(UPDATED_ALAMAT)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMEkspedisiMockMvc.perform(put("/api/m-ekspedisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMEkspedisi)))
            .andExpect(status().isOk());

        // Validate the MEkspedisi in the database
        List<MEkspedisi> mEkspedisiList = mEkspedisiRepository.findAll();
        assertThat(mEkspedisiList).hasSize(databaseSizeBeforeUpdate);
        MEkspedisi testMEkspedisi = mEkspedisiList.get(mEkspedisiList.size() - 1);
        assertThat(testMEkspedisi.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMEkspedisi.getTelepon()).isEqualTo(UPDATED_TELEPON);
        assertThat(testMEkspedisi.getMobilephone()).isEqualTo(UPDATED_MOBILEPHONE);
        assertThat(testMEkspedisi.getAlamat()).isEqualTo(UPDATED_ALAMAT);
        assertThat(testMEkspedisi.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMEkspedisi.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMEkspedisi() throws Exception {
        int databaseSizeBeforeUpdate = mEkspedisiRepository.findAll().size();

        // Create the MEkspedisi

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMEkspedisiMockMvc.perform(put("/api/m-ekspedisis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mEkspedisi)))
            .andExpect(status().isBadRequest());

        // Validate the MEkspedisi in the database
        List<MEkspedisi> mEkspedisiList = mEkspedisiRepository.findAll();
        assertThat(mEkspedisiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMEkspedisi() throws Exception {
        // Initialize the database
        mEkspedisiService.save(mEkspedisi);

        int databaseSizeBeforeDelete = mEkspedisiRepository.findAll().size();

        // Get the mEkspedisi
        restMEkspedisiMockMvc.perform(delete("/api/m-ekspedisis/{id}", mEkspedisi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MEkspedisi> mEkspedisiList = mEkspedisiRepository.findAll();
        assertThat(mEkspedisiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MEkspedisi.class);
        MEkspedisi mEkspedisi1 = new MEkspedisi();
        mEkspedisi1.setId(1L);
        MEkspedisi mEkspedisi2 = new MEkspedisi();
        mEkspedisi2.setId(mEkspedisi1.getId());
        assertThat(mEkspedisi1).isEqualTo(mEkspedisi2);
        mEkspedisi2.setId(2L);
        assertThat(mEkspedisi1).isNotEqualTo(mEkspedisi2);
        mEkspedisi1.setId(null);
        assertThat(mEkspedisi1).isNotEqualTo(mEkspedisi2);
    }
}

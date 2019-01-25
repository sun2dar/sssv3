package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MSatuan;
import com.sssv3.repository.MSatuanRepository;
import com.sssv3.service.MSatuanService;
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
 * Test class for the MSatuanResource REST controller.
 *
 * @see MSatuanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MSatuanResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MSatuanRepository mSatuanRepository;

    @Autowired
    private MSatuanService mSatuanService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMSatuanMockMvc;

    private MSatuan mSatuan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MSatuanResource mSatuanResource = new MSatuanResource(mSatuanService);
        this.restMSatuanMockMvc = MockMvcBuilders.standaloneSetup(mSatuanResource)
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
    public static MSatuan createEntity(EntityManager em) {
        MSatuan mSatuan = new MSatuan()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mSatuan;
    }

    @Before
    public void initTest() {
        mSatuan = createEntity(em);
    }

    @Test
    @Transactional
    public void createMSatuan() throws Exception {
        int databaseSizeBeforeCreate = mSatuanRepository.findAll().size();

        // Create the MSatuan
        restMSatuanMockMvc.perform(post("/api/m-satuans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mSatuan)))
            .andExpect(status().isCreated());

        // Validate the MSatuan in the database
        List<MSatuan> mSatuanList = mSatuanRepository.findAll();
        assertThat(mSatuanList).hasSize(databaseSizeBeforeCreate + 1);
        MSatuan testMSatuan = mSatuanList.get(mSatuanList.size() - 1);
        assertThat(testMSatuan.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMSatuan.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMSatuan.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMSatuan.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMSatuanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mSatuanRepository.findAll().size();

        // Create the MSatuan with an existing ID
        mSatuan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMSatuanMockMvc.perform(post("/api/m-satuans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mSatuan)))
            .andExpect(status().isBadRequest());

        // Validate the MSatuan in the database
        List<MSatuan> mSatuanList = mSatuanRepository.findAll();
        assertThat(mSatuanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mSatuanRepository.findAll().size();
        // set the field null
        mSatuan.setNama(null);

        // Create the MSatuan, which fails.

        restMSatuanMockMvc.perform(post("/api/m-satuans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mSatuan)))
            .andExpect(status().isBadRequest());

        List<MSatuan> mSatuanList = mSatuanRepository.findAll();
        assertThat(mSatuanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMSatuans() throws Exception {
        // Initialize the database
        mSatuanRepository.saveAndFlush(mSatuan);

        // Get all the mSatuanList
        restMSatuanMockMvc.perform(get("/api/m-satuans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mSatuan.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMSatuan() throws Exception {
        // Initialize the database
        mSatuanRepository.saveAndFlush(mSatuan);

        // Get the mSatuan
        restMSatuanMockMvc.perform(get("/api/m-satuans/{id}", mSatuan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mSatuan.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMSatuan() throws Exception {
        // Get the mSatuan
        restMSatuanMockMvc.perform(get("/api/m-satuans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMSatuan() throws Exception {
        // Initialize the database
        mSatuanService.save(mSatuan);

        int databaseSizeBeforeUpdate = mSatuanRepository.findAll().size();

        // Update the mSatuan
        MSatuan updatedMSatuan = mSatuanRepository.findById(mSatuan.getId()).get();
        // Disconnect from session so that the updates on updatedMSatuan are not directly saved in db
        em.detach(updatedMSatuan);
        updatedMSatuan
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMSatuanMockMvc.perform(put("/api/m-satuans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMSatuan)))
            .andExpect(status().isOk());

        // Validate the MSatuan in the database
        List<MSatuan> mSatuanList = mSatuanRepository.findAll();
        assertThat(mSatuanList).hasSize(databaseSizeBeforeUpdate);
        MSatuan testMSatuan = mSatuanList.get(mSatuanList.size() - 1);
        assertThat(testMSatuan.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMSatuan.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMSatuan.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMSatuan.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMSatuan() throws Exception {
        int databaseSizeBeforeUpdate = mSatuanRepository.findAll().size();

        // Create the MSatuan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMSatuanMockMvc.perform(put("/api/m-satuans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mSatuan)))
            .andExpect(status().isBadRequest());

        // Validate the MSatuan in the database
        List<MSatuan> mSatuanList = mSatuanRepository.findAll();
        assertThat(mSatuanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMSatuan() throws Exception {
        // Initialize the database
        mSatuanService.save(mSatuan);

        int databaseSizeBeforeDelete = mSatuanRepository.findAll().size();

        // Get the mSatuan
        restMSatuanMockMvc.perform(delete("/api/m-satuans/{id}", mSatuan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MSatuan> mSatuanList = mSatuanRepository.findAll();
        assertThat(mSatuanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MSatuan.class);
        MSatuan mSatuan1 = new MSatuan();
        mSatuan1.setId(1L);
        MSatuan mSatuan2 = new MSatuan();
        mSatuan2.setId(mSatuan1.getId());
        assertThat(mSatuan1).isEqualTo(mSatuan2);
        mSatuan2.setId(2L);
        assertThat(mSatuan1).isNotEqualTo(mSatuan2);
        mSatuan1.setId(null);
        assertThat(mSatuan1).isNotEqualTo(mSatuan2);
    }
}

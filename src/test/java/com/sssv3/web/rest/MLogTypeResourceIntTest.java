package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MLogType;
import com.sssv3.repository.MLogTypeRepository;
import com.sssv3.service.MLogTypeService;
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
 * Test class for the MLogTypeResource REST controller.
 *
 * @see MLogTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MLogTypeResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MLogTypeRepository mLogTypeRepository;

    @Autowired
    private MLogTypeService mLogTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMLogTypeMockMvc;

    private MLogType mLogType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MLogTypeResource mLogTypeResource = new MLogTypeResource(mLogTypeService);
        this.restMLogTypeMockMvc = MockMvcBuilders.standaloneSetup(mLogTypeResource)
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
    public static MLogType createEntity(EntityManager em) {
        MLogType mLogType = new MLogType()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mLogType;
    }

    @Before
    public void initTest() {
        mLogType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMLogType() throws Exception {
        int databaseSizeBeforeCreate = mLogTypeRepository.findAll().size();

        // Create the MLogType
        restMLogTypeMockMvc.perform(post("/api/m-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogType)))
            .andExpect(status().isCreated());

        // Validate the MLogType in the database
        List<MLogType> mLogTypeList = mLogTypeRepository.findAll();
        assertThat(mLogTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MLogType testMLogType = mLogTypeList.get(mLogTypeList.size() - 1);
        assertThat(testMLogType.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMLogType.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMLogType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMLogType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMLogTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mLogTypeRepository.findAll().size();

        // Create the MLogType with an existing ID
        mLogType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMLogTypeMockMvc.perform(post("/api/m-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogType)))
            .andExpect(status().isBadRequest());

        // Validate the MLogType in the database
        List<MLogType> mLogTypeList = mLogTypeRepository.findAll();
        assertThat(mLogTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mLogTypeRepository.findAll().size();
        // set the field null
        mLogType.setNama(null);

        // Create the MLogType, which fails.

        restMLogTypeMockMvc.perform(post("/api/m-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogType)))
            .andExpect(status().isBadRequest());

        List<MLogType> mLogTypeList = mLogTypeRepository.findAll();
        assertThat(mLogTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMLogTypes() throws Exception {
        // Initialize the database
        mLogTypeRepository.saveAndFlush(mLogType);

        // Get all the mLogTypeList
        restMLogTypeMockMvc.perform(get("/api/m-log-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mLogType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMLogType() throws Exception {
        // Initialize the database
        mLogTypeRepository.saveAndFlush(mLogType);

        // Get the mLogType
        restMLogTypeMockMvc.perform(get("/api/m-log-types/{id}", mLogType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mLogType.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMLogType() throws Exception {
        // Get the mLogType
        restMLogTypeMockMvc.perform(get("/api/m-log-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMLogType() throws Exception {
        // Initialize the database
        mLogTypeService.save(mLogType);

        int databaseSizeBeforeUpdate = mLogTypeRepository.findAll().size();

        // Update the mLogType
        MLogType updatedMLogType = mLogTypeRepository.findById(mLogType.getId()).get();
        // Disconnect from session so that the updates on updatedMLogType are not directly saved in db
        em.detach(updatedMLogType);
        updatedMLogType
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMLogTypeMockMvc.perform(put("/api/m-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMLogType)))
            .andExpect(status().isOk());

        // Validate the MLogType in the database
        List<MLogType> mLogTypeList = mLogTypeRepository.findAll();
        assertThat(mLogTypeList).hasSize(databaseSizeBeforeUpdate);
        MLogType testMLogType = mLogTypeList.get(mLogTypeList.size() - 1);
        assertThat(testMLogType.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMLogType.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMLogType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMLogType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMLogType() throws Exception {
        int databaseSizeBeforeUpdate = mLogTypeRepository.findAll().size();

        // Create the MLogType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMLogTypeMockMvc.perform(put("/api/m-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogType)))
            .andExpect(status().isBadRequest());

        // Validate the MLogType in the database
        List<MLogType> mLogTypeList = mLogTypeRepository.findAll();
        assertThat(mLogTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMLogType() throws Exception {
        // Initialize the database
        mLogTypeService.save(mLogType);

        int databaseSizeBeforeDelete = mLogTypeRepository.findAll().size();

        // Get the mLogType
        restMLogTypeMockMvc.perform(delete("/api/m-log-types/{id}", mLogType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MLogType> mLogTypeList = mLogTypeRepository.findAll();
        assertThat(mLogTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MLogType.class);
        MLogType mLogType1 = new MLogType();
        mLogType1.setId(1L);
        MLogType mLogType2 = new MLogType();
        mLogType2.setId(mLogType1.getId());
        assertThat(mLogType1).isEqualTo(mLogType2);
        mLogType2.setId(2L);
        assertThat(mLogType1).isNotEqualTo(mLogType2);
        mLogType1.setId(null);
        assertThat(mLogType1).isNotEqualTo(mLogType2);
    }
}

package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MOperasionalType;
import com.sssv3.repository.MOperasionalTypeRepository;
import com.sssv3.service.MOperasionalTypeService;
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
 * Test class for the MOperasionalTypeResource REST controller.
 *
 * @see MOperasionalTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MOperasionalTypeResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MOperasionalTypeRepository mOperasionalTypeRepository;

    @Autowired
    private MOperasionalTypeService mOperasionalTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMOperasionalTypeMockMvc;

    private MOperasionalType mOperasionalType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MOperasionalTypeResource mOperasionalTypeResource = new MOperasionalTypeResource(mOperasionalTypeService);
        this.restMOperasionalTypeMockMvc = MockMvcBuilders.standaloneSetup(mOperasionalTypeResource)
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
    public static MOperasionalType createEntity(EntityManager em) {
        MOperasionalType mOperasionalType = new MOperasionalType()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mOperasionalType;
    }

    @Before
    public void initTest() {
        mOperasionalType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMOperasionalType() throws Exception {
        int databaseSizeBeforeCreate = mOperasionalTypeRepository.findAll().size();

        // Create the MOperasionalType
        restMOperasionalTypeMockMvc.perform(post("/api/m-operasional-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mOperasionalType)))
            .andExpect(status().isCreated());

        // Validate the MOperasionalType in the database
        List<MOperasionalType> mOperasionalTypeList = mOperasionalTypeRepository.findAll();
        assertThat(mOperasionalTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MOperasionalType testMOperasionalType = mOperasionalTypeList.get(mOperasionalTypeList.size() - 1);
        assertThat(testMOperasionalType.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMOperasionalType.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMOperasionalType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMOperasionalType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMOperasionalTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mOperasionalTypeRepository.findAll().size();

        // Create the MOperasionalType with an existing ID
        mOperasionalType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMOperasionalTypeMockMvc.perform(post("/api/m-operasional-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mOperasionalType)))
            .andExpect(status().isBadRequest());

        // Validate the MOperasionalType in the database
        List<MOperasionalType> mOperasionalTypeList = mOperasionalTypeRepository.findAll();
        assertThat(mOperasionalTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mOperasionalTypeRepository.findAll().size();
        // set the field null
        mOperasionalType.setNama(null);

        // Create the MOperasionalType, which fails.

        restMOperasionalTypeMockMvc.perform(post("/api/m-operasional-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mOperasionalType)))
            .andExpect(status().isBadRequest());

        List<MOperasionalType> mOperasionalTypeList = mOperasionalTypeRepository.findAll();
        assertThat(mOperasionalTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMOperasionalTypes() throws Exception {
        // Initialize the database
        mOperasionalTypeRepository.saveAndFlush(mOperasionalType);

        // Get all the mOperasionalTypeList
        restMOperasionalTypeMockMvc.perform(get("/api/m-operasional-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mOperasionalType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMOperasionalType() throws Exception {
        // Initialize the database
        mOperasionalTypeRepository.saveAndFlush(mOperasionalType);

        // Get the mOperasionalType
        restMOperasionalTypeMockMvc.perform(get("/api/m-operasional-types/{id}", mOperasionalType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mOperasionalType.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMOperasionalType() throws Exception {
        // Get the mOperasionalType
        restMOperasionalTypeMockMvc.perform(get("/api/m-operasional-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMOperasionalType() throws Exception {
        // Initialize the database
        mOperasionalTypeService.save(mOperasionalType);

        int databaseSizeBeforeUpdate = mOperasionalTypeRepository.findAll().size();

        // Update the mOperasionalType
        MOperasionalType updatedMOperasionalType = mOperasionalTypeRepository.findById(mOperasionalType.getId()).get();
        // Disconnect from session so that the updates on updatedMOperasionalType are not directly saved in db
        em.detach(updatedMOperasionalType);
        updatedMOperasionalType
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMOperasionalTypeMockMvc.perform(put("/api/m-operasional-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMOperasionalType)))
            .andExpect(status().isOk());

        // Validate the MOperasionalType in the database
        List<MOperasionalType> mOperasionalTypeList = mOperasionalTypeRepository.findAll();
        assertThat(mOperasionalTypeList).hasSize(databaseSizeBeforeUpdate);
        MOperasionalType testMOperasionalType = mOperasionalTypeList.get(mOperasionalTypeList.size() - 1);
        assertThat(testMOperasionalType.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMOperasionalType.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMOperasionalType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMOperasionalType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMOperasionalType() throws Exception {
        int databaseSizeBeforeUpdate = mOperasionalTypeRepository.findAll().size();

        // Create the MOperasionalType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMOperasionalTypeMockMvc.perform(put("/api/m-operasional-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mOperasionalType)))
            .andExpect(status().isBadRequest());

        // Validate the MOperasionalType in the database
        List<MOperasionalType> mOperasionalTypeList = mOperasionalTypeRepository.findAll();
        assertThat(mOperasionalTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMOperasionalType() throws Exception {
        // Initialize the database
        mOperasionalTypeService.save(mOperasionalType);

        int databaseSizeBeforeDelete = mOperasionalTypeRepository.findAll().size();

        // Get the mOperasionalType
        restMOperasionalTypeMockMvc.perform(delete("/api/m-operasional-types/{id}", mOperasionalType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MOperasionalType> mOperasionalTypeList = mOperasionalTypeRepository.findAll();
        assertThat(mOperasionalTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MOperasionalType.class);
        MOperasionalType mOperasionalType1 = new MOperasionalType();
        mOperasionalType1.setId(1L);
        MOperasionalType mOperasionalType2 = new MOperasionalType();
        mOperasionalType2.setId(mOperasionalType1.getId());
        assertThat(mOperasionalType1).isEqualTo(mOperasionalType2);
        mOperasionalType2.setId(2L);
        assertThat(mOperasionalType1).isNotEqualTo(mOperasionalType2);
        mOperasionalType1.setId(null);
        assertThat(mOperasionalType1).isNotEqualTo(mOperasionalType2);
    }
}

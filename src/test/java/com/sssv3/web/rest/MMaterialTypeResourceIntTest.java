package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MMaterialType;
import com.sssv3.repository.MMaterialTypeRepository;
import com.sssv3.service.MMaterialTypeService;
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
 * Test class for the MMaterialTypeResource REST controller.
 *
 * @see MMaterialTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MMaterialTypeResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MMaterialTypeRepository mMaterialTypeRepository;

    @Autowired
    private MMaterialTypeService mMaterialTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMMaterialTypeMockMvc;

    private MMaterialType mMaterialType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MMaterialTypeResource mMaterialTypeResource = new MMaterialTypeResource(mMaterialTypeService);
        this.restMMaterialTypeMockMvc = MockMvcBuilders.standaloneSetup(mMaterialTypeResource)
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
    public static MMaterialType createEntity(EntityManager em) {
        MMaterialType mMaterialType = new MMaterialType()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mMaterialType;
    }

    @Before
    public void initTest() {
        mMaterialType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMMaterialType() throws Exception {
        int databaseSizeBeforeCreate = mMaterialTypeRepository.findAll().size();

        // Create the MMaterialType
        restMMaterialTypeMockMvc.perform(post("/api/m-material-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMaterialType)))
            .andExpect(status().isCreated());

        // Validate the MMaterialType in the database
        List<MMaterialType> mMaterialTypeList = mMaterialTypeRepository.findAll();
        assertThat(mMaterialTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MMaterialType testMMaterialType = mMaterialTypeList.get(mMaterialTypeList.size() - 1);
        assertThat(testMMaterialType.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMMaterialType.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMMaterialType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMMaterialType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMMaterialTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mMaterialTypeRepository.findAll().size();

        // Create the MMaterialType with an existing ID
        mMaterialType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMMaterialTypeMockMvc.perform(post("/api/m-material-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMaterialType)))
            .andExpect(status().isBadRequest());

        // Validate the MMaterialType in the database
        List<MMaterialType> mMaterialTypeList = mMaterialTypeRepository.findAll();
        assertThat(mMaterialTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mMaterialTypeRepository.findAll().size();
        // set the field null
        mMaterialType.setNama(null);

        // Create the MMaterialType, which fails.

        restMMaterialTypeMockMvc.perform(post("/api/m-material-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMaterialType)))
            .andExpect(status().isBadRequest());

        List<MMaterialType> mMaterialTypeList = mMaterialTypeRepository.findAll();
        assertThat(mMaterialTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMMaterialTypes() throws Exception {
        // Initialize the database
        mMaterialTypeRepository.saveAndFlush(mMaterialType);

        // Get all the mMaterialTypeList
        restMMaterialTypeMockMvc.perform(get("/api/m-material-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMaterialType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMMaterialType() throws Exception {
        // Initialize the database
        mMaterialTypeRepository.saveAndFlush(mMaterialType);

        // Get the mMaterialType
        restMMaterialTypeMockMvc.perform(get("/api/m-material-types/{id}", mMaterialType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mMaterialType.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMMaterialType() throws Exception {
        // Get the mMaterialType
        restMMaterialTypeMockMvc.perform(get("/api/m-material-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMMaterialType() throws Exception {
        // Initialize the database
        mMaterialTypeService.save(mMaterialType);

        int databaseSizeBeforeUpdate = mMaterialTypeRepository.findAll().size();

        // Update the mMaterialType
        MMaterialType updatedMMaterialType = mMaterialTypeRepository.findById(mMaterialType.getId()).get();
        // Disconnect from session so that the updates on updatedMMaterialType are not directly saved in db
        em.detach(updatedMMaterialType);
        updatedMMaterialType
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMMaterialTypeMockMvc.perform(put("/api/m-material-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMMaterialType)))
            .andExpect(status().isOk());

        // Validate the MMaterialType in the database
        List<MMaterialType> mMaterialTypeList = mMaterialTypeRepository.findAll();
        assertThat(mMaterialTypeList).hasSize(databaseSizeBeforeUpdate);
        MMaterialType testMMaterialType = mMaterialTypeList.get(mMaterialTypeList.size() - 1);
        assertThat(testMMaterialType.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMMaterialType.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMMaterialType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMMaterialType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMMaterialType() throws Exception {
        int databaseSizeBeforeUpdate = mMaterialTypeRepository.findAll().size();

        // Create the MMaterialType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMaterialTypeMockMvc.perform(put("/api/m-material-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMaterialType)))
            .andExpect(status().isBadRequest());

        // Validate the MMaterialType in the database
        List<MMaterialType> mMaterialTypeList = mMaterialTypeRepository.findAll();
        assertThat(mMaterialTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMMaterialType() throws Exception {
        // Initialize the database
        mMaterialTypeService.save(mMaterialType);

        int databaseSizeBeforeDelete = mMaterialTypeRepository.findAll().size();

        // Get the mMaterialType
        restMMaterialTypeMockMvc.perform(delete("/api/m-material-types/{id}", mMaterialType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MMaterialType> mMaterialTypeList = mMaterialTypeRepository.findAll();
        assertThat(mMaterialTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MMaterialType.class);
        MMaterialType mMaterialType1 = new MMaterialType();
        mMaterialType1.setId(1L);
        MMaterialType mMaterialType2 = new MMaterialType();
        mMaterialType2.setId(mMaterialType1.getId());
        assertThat(mMaterialType1).isEqualTo(mMaterialType2);
        mMaterialType2.setId(2L);
        assertThat(mMaterialType1).isNotEqualTo(mMaterialType2);
        mMaterialType1.setId(null);
        assertThat(mMaterialType1).isNotEqualTo(mMaterialType2);
    }
}

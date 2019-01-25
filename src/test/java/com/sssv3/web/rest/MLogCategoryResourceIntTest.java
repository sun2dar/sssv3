package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MLogCategory;
import com.sssv3.repository.MLogCategoryRepository;
import com.sssv3.service.MLogCategoryService;
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
 * Test class for the MLogCategoryResource REST controller.
 *
 * @see MLogCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MLogCategoryResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final Double DEFAULT_DIAMETER_1 = 1D;
    private static final Double UPDATED_DIAMETER_1 = 2D;

    private static final Double DEFAULT_DIAMETER_2 = 1D;
    private static final Double UPDATED_DIAMETER_2 = 2D;

    private static final Double DEFAULT_HARGA_BELI = 1D;
    private static final Double UPDATED_HARGA_BELI = 2D;

    private static final Double DEFAULT_HARGA_JUAL = 1D;
    private static final Double UPDATED_HARGA_JUAL = 2D;

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MLogCategoryRepository mLogCategoryRepository;

    @Autowired
    private MLogCategoryService mLogCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMLogCategoryMockMvc;

    private MLogCategory mLogCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MLogCategoryResource mLogCategoryResource = new MLogCategoryResource(mLogCategoryService);
        this.restMLogCategoryMockMvc = MockMvcBuilders.standaloneSetup(mLogCategoryResource)
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
    public static MLogCategory createEntity(EntityManager em) {
        MLogCategory mLogCategory = new MLogCategory()
            .nama(DEFAULT_NAMA)
            .diameter1(DEFAULT_DIAMETER_1)
            .diameter2(DEFAULT_DIAMETER_2)
            .hargaBeli(DEFAULT_HARGA_BELI)
            .hargaJual(DEFAULT_HARGA_JUAL)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mLogCategory;
    }

    @Before
    public void initTest() {
        mLogCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMLogCategory() throws Exception {
        int databaseSizeBeforeCreate = mLogCategoryRepository.findAll().size();

        // Create the MLogCategory
        restMLogCategoryMockMvc.perform(post("/api/m-log-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogCategory)))
            .andExpect(status().isCreated());

        // Validate the MLogCategory in the database
        List<MLogCategory> mLogCategoryList = mLogCategoryRepository.findAll();
        assertThat(mLogCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        MLogCategory testMLogCategory = mLogCategoryList.get(mLogCategoryList.size() - 1);
        assertThat(testMLogCategory.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMLogCategory.getDiameter1()).isEqualTo(DEFAULT_DIAMETER_1);
        assertThat(testMLogCategory.getDiameter2()).isEqualTo(DEFAULT_DIAMETER_2);
        assertThat(testMLogCategory.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testMLogCategory.getHargaJual()).isEqualTo(DEFAULT_HARGA_JUAL);
        assertThat(testMLogCategory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMLogCategory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMLogCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mLogCategoryRepository.findAll().size();

        // Create the MLogCategory with an existing ID
        mLogCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMLogCategoryMockMvc.perform(post("/api/m-log-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogCategory)))
            .andExpect(status().isBadRequest());

        // Validate the MLogCategory in the database
        List<MLogCategory> mLogCategoryList = mLogCategoryRepository.findAll();
        assertThat(mLogCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mLogCategoryRepository.findAll().size();
        // set the field null
        mLogCategory.setNama(null);

        // Create the MLogCategory, which fails.

        restMLogCategoryMockMvc.perform(post("/api/m-log-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogCategory)))
            .andExpect(status().isBadRequest());

        List<MLogCategory> mLogCategoryList = mLogCategoryRepository.findAll();
        assertThat(mLogCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiameter1IsRequired() throws Exception {
        int databaseSizeBeforeTest = mLogCategoryRepository.findAll().size();
        // set the field null
        mLogCategory.setDiameter1(null);

        // Create the MLogCategory, which fails.

        restMLogCategoryMockMvc.perform(post("/api/m-log-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogCategory)))
            .andExpect(status().isBadRequest());

        List<MLogCategory> mLogCategoryList = mLogCategoryRepository.findAll();
        assertThat(mLogCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiameter2IsRequired() throws Exception {
        int databaseSizeBeforeTest = mLogCategoryRepository.findAll().size();
        // set the field null
        mLogCategory.setDiameter2(null);

        // Create the MLogCategory, which fails.

        restMLogCategoryMockMvc.perform(post("/api/m-log-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogCategory)))
            .andExpect(status().isBadRequest());

        List<MLogCategory> mLogCategoryList = mLogCategoryRepository.findAll();
        assertThat(mLogCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHargaBeliIsRequired() throws Exception {
        int databaseSizeBeforeTest = mLogCategoryRepository.findAll().size();
        // set the field null
        mLogCategory.setHargaBeli(null);

        // Create the MLogCategory, which fails.

        restMLogCategoryMockMvc.perform(post("/api/m-log-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogCategory)))
            .andExpect(status().isBadRequest());

        List<MLogCategory> mLogCategoryList = mLogCategoryRepository.findAll();
        assertThat(mLogCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMLogCategories() throws Exception {
        // Initialize the database
        mLogCategoryRepository.saveAndFlush(mLogCategory);

        // Get all the mLogCategoryList
        restMLogCategoryMockMvc.perform(get("/api/m-log-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mLogCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].diameter1").value(hasItem(DEFAULT_DIAMETER_1.doubleValue())))
            .andExpect(jsonPath("$.[*].diameter2").value(hasItem(DEFAULT_DIAMETER_2.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaJual").value(hasItem(DEFAULT_HARGA_JUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMLogCategory() throws Exception {
        // Initialize the database
        mLogCategoryRepository.saveAndFlush(mLogCategory);

        // Get the mLogCategory
        restMLogCategoryMockMvc.perform(get("/api/m-log-categories/{id}", mLogCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mLogCategory.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.diameter1").value(DEFAULT_DIAMETER_1.doubleValue()))
            .andExpect(jsonPath("$.diameter2").value(DEFAULT_DIAMETER_2.doubleValue()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.doubleValue()))
            .andExpect(jsonPath("$.hargaJual").value(DEFAULT_HARGA_JUAL.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMLogCategory() throws Exception {
        // Get the mLogCategory
        restMLogCategoryMockMvc.perform(get("/api/m-log-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMLogCategory() throws Exception {
        // Initialize the database
        mLogCategoryService.save(mLogCategory);

        int databaseSizeBeforeUpdate = mLogCategoryRepository.findAll().size();

        // Update the mLogCategory
        MLogCategory updatedMLogCategory = mLogCategoryRepository.findById(mLogCategory.getId()).get();
        // Disconnect from session so that the updates on updatedMLogCategory are not directly saved in db
        em.detach(updatedMLogCategory);
        updatedMLogCategory
            .nama(UPDATED_NAMA)
            .diameter1(UPDATED_DIAMETER_1)
            .diameter2(UPDATED_DIAMETER_2)
            .hargaBeli(UPDATED_HARGA_BELI)
            .hargaJual(UPDATED_HARGA_JUAL)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMLogCategoryMockMvc.perform(put("/api/m-log-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMLogCategory)))
            .andExpect(status().isOk());

        // Validate the MLogCategory in the database
        List<MLogCategory> mLogCategoryList = mLogCategoryRepository.findAll();
        assertThat(mLogCategoryList).hasSize(databaseSizeBeforeUpdate);
        MLogCategory testMLogCategory = mLogCategoryList.get(mLogCategoryList.size() - 1);
        assertThat(testMLogCategory.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMLogCategory.getDiameter1()).isEqualTo(UPDATED_DIAMETER_1);
        assertThat(testMLogCategory.getDiameter2()).isEqualTo(UPDATED_DIAMETER_2);
        assertThat(testMLogCategory.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testMLogCategory.getHargaJual()).isEqualTo(UPDATED_HARGA_JUAL);
        assertThat(testMLogCategory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMLogCategory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMLogCategory() throws Exception {
        int databaseSizeBeforeUpdate = mLogCategoryRepository.findAll().size();

        // Create the MLogCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMLogCategoryMockMvc.perform(put("/api/m-log-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLogCategory)))
            .andExpect(status().isBadRequest());

        // Validate the MLogCategory in the database
        List<MLogCategory> mLogCategoryList = mLogCategoryRepository.findAll();
        assertThat(mLogCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMLogCategory() throws Exception {
        // Initialize the database
        mLogCategoryService.save(mLogCategory);

        int databaseSizeBeforeDelete = mLogCategoryRepository.findAll().size();

        // Get the mLogCategory
        restMLogCategoryMockMvc.perform(delete("/api/m-log-categories/{id}", mLogCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MLogCategory> mLogCategoryList = mLogCategoryRepository.findAll();
        assertThat(mLogCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MLogCategory.class);
        MLogCategory mLogCategory1 = new MLogCategory();
        mLogCategory1.setId(1L);
        MLogCategory mLogCategory2 = new MLogCategory();
        mLogCategory2.setId(mLogCategory1.getId());
        assertThat(mLogCategory1).isEqualTo(mLogCategory2);
        mLogCategory2.setId(2L);
        assertThat(mLogCategory1).isNotEqualTo(mLogCategory2);
        mLogCategory1.setId(null);
        assertThat(mLogCategory1).isNotEqualTo(mLogCategory2);
    }
}

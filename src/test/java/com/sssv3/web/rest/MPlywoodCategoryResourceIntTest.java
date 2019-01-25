package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MPlywoodCategory;
import com.sssv3.repository.MPlywoodCategoryRepository;
import com.sssv3.service.MPlywoodCategoryService;
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
 * Test class for the MPlywoodCategoryResource REST controller.
 *
 * @see MPlywoodCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MPlywoodCategoryResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Double DEFAULT_TEBAL = 1D;
    private static final Double UPDATED_TEBAL = 2D;

    private static final Double DEFAULT_PANJANG = 1D;
    private static final Double UPDATED_PANJANG = 2D;

    private static final Double DEFAULT_LEBAR = 1D;
    private static final Double UPDATED_LEBAR = 2D;

    private static final Double DEFAULT_HARGA_BELI = 1D;
    private static final Double UPDATED_HARGA_BELI = 2D;

    private static final Double DEFAULT_HARGA_JUAL = 1D;
    private static final Double UPDATED_HARGA_JUAL = 2D;

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MPlywoodCategoryRepository mPlywoodCategoryRepository;

    @Autowired
    private MPlywoodCategoryService mPlywoodCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMPlywoodCategoryMockMvc;

    private MPlywoodCategory mPlywoodCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MPlywoodCategoryResource mPlywoodCategoryResource = new MPlywoodCategoryResource(mPlywoodCategoryService);
        this.restMPlywoodCategoryMockMvc = MockMvcBuilders.standaloneSetup(mPlywoodCategoryResource)
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
    public static MPlywoodCategory createEntity(EntityManager em) {
        MPlywoodCategory mPlywoodCategory = new MPlywoodCategory()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .tebal(DEFAULT_TEBAL)
            .panjang(DEFAULT_PANJANG)
            .lebar(DEFAULT_LEBAR)
            .hargaBeli(DEFAULT_HARGA_BELI)
            .hargaJual(DEFAULT_HARGA_JUAL)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mPlywoodCategory;
    }

    @Before
    public void initTest() {
        mPlywoodCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPlywoodCategory() throws Exception {
        int databaseSizeBeforeCreate = mPlywoodCategoryRepository.findAll().size();

        // Create the MPlywoodCategory
        restMPlywoodCategoryMockMvc.perform(post("/api/m-plywood-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywoodCategory)))
            .andExpect(status().isCreated());

        // Validate the MPlywoodCategory in the database
        List<MPlywoodCategory> mPlywoodCategoryList = mPlywoodCategoryRepository.findAll();
        assertThat(mPlywoodCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        MPlywoodCategory testMPlywoodCategory = mPlywoodCategoryList.get(mPlywoodCategoryList.size() - 1);
        assertThat(testMPlywoodCategory.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMPlywoodCategory.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMPlywoodCategory.getTebal()).isEqualTo(DEFAULT_TEBAL);
        assertThat(testMPlywoodCategory.getPanjang()).isEqualTo(DEFAULT_PANJANG);
        assertThat(testMPlywoodCategory.getLebar()).isEqualTo(DEFAULT_LEBAR);
        assertThat(testMPlywoodCategory.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testMPlywoodCategory.getHargaJual()).isEqualTo(DEFAULT_HARGA_JUAL);
        assertThat(testMPlywoodCategory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMPlywoodCategory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMPlywoodCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPlywoodCategoryRepository.findAll().size();

        // Create the MPlywoodCategory with an existing ID
        mPlywoodCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPlywoodCategoryMockMvc.perform(post("/api/m-plywood-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywoodCategory)))
            .andExpect(status().isBadRequest());

        // Validate the MPlywoodCategory in the database
        List<MPlywoodCategory> mPlywoodCategoryList = mPlywoodCategoryRepository.findAll();
        assertThat(mPlywoodCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPlywoodCategoryRepository.findAll().size();
        // set the field null
        mPlywoodCategory.setNama(null);

        // Create the MPlywoodCategory, which fails.

        restMPlywoodCategoryMockMvc.perform(post("/api/m-plywood-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywoodCategory)))
            .andExpect(status().isBadRequest());

        List<MPlywoodCategory> mPlywoodCategoryList = mPlywoodCategoryRepository.findAll();
        assertThat(mPlywoodCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMPlywoodCategories() throws Exception {
        // Initialize the database
        mPlywoodCategoryRepository.saveAndFlush(mPlywoodCategory);

        // Get all the mPlywoodCategoryList
        restMPlywoodCategoryMockMvc.perform(get("/api/m-plywood-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPlywoodCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].tebal").value(hasItem(DEFAULT_TEBAL.doubleValue())))
            .andExpect(jsonPath("$.[*].panjang").value(hasItem(DEFAULT_PANJANG.doubleValue())))
            .andExpect(jsonPath("$.[*].lebar").value(hasItem(DEFAULT_LEBAR.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaJual").value(hasItem(DEFAULT_HARGA_JUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMPlywoodCategory() throws Exception {
        // Initialize the database
        mPlywoodCategoryRepository.saveAndFlush(mPlywoodCategory);

        // Get the mPlywoodCategory
        restMPlywoodCategoryMockMvc.perform(get("/api/m-plywood-categories/{id}", mPlywoodCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mPlywoodCategory.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.tebal").value(DEFAULT_TEBAL.doubleValue()))
            .andExpect(jsonPath("$.panjang").value(DEFAULT_PANJANG.doubleValue()))
            .andExpect(jsonPath("$.lebar").value(DEFAULT_LEBAR.doubleValue()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.doubleValue()))
            .andExpect(jsonPath("$.hargaJual").value(DEFAULT_HARGA_JUAL.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMPlywoodCategory() throws Exception {
        // Get the mPlywoodCategory
        restMPlywoodCategoryMockMvc.perform(get("/api/m-plywood-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPlywoodCategory() throws Exception {
        // Initialize the database
        mPlywoodCategoryService.save(mPlywoodCategory);

        int databaseSizeBeforeUpdate = mPlywoodCategoryRepository.findAll().size();

        // Update the mPlywoodCategory
        MPlywoodCategory updatedMPlywoodCategory = mPlywoodCategoryRepository.findById(mPlywoodCategory.getId()).get();
        // Disconnect from session so that the updates on updatedMPlywoodCategory are not directly saved in db
        em.detach(updatedMPlywoodCategory);
        updatedMPlywoodCategory
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .tebal(UPDATED_TEBAL)
            .panjang(UPDATED_PANJANG)
            .lebar(UPDATED_LEBAR)
            .hargaBeli(UPDATED_HARGA_BELI)
            .hargaJual(UPDATED_HARGA_JUAL)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMPlywoodCategoryMockMvc.perform(put("/api/m-plywood-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMPlywoodCategory)))
            .andExpect(status().isOk());

        // Validate the MPlywoodCategory in the database
        List<MPlywoodCategory> mPlywoodCategoryList = mPlywoodCategoryRepository.findAll();
        assertThat(mPlywoodCategoryList).hasSize(databaseSizeBeforeUpdate);
        MPlywoodCategory testMPlywoodCategory = mPlywoodCategoryList.get(mPlywoodCategoryList.size() - 1);
        assertThat(testMPlywoodCategory.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMPlywoodCategory.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMPlywoodCategory.getTebal()).isEqualTo(UPDATED_TEBAL);
        assertThat(testMPlywoodCategory.getPanjang()).isEqualTo(UPDATED_PANJANG);
        assertThat(testMPlywoodCategory.getLebar()).isEqualTo(UPDATED_LEBAR);
        assertThat(testMPlywoodCategory.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testMPlywoodCategory.getHargaJual()).isEqualTo(UPDATED_HARGA_JUAL);
        assertThat(testMPlywoodCategory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMPlywoodCategory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMPlywoodCategory() throws Exception {
        int databaseSizeBeforeUpdate = mPlywoodCategoryRepository.findAll().size();

        // Create the MPlywoodCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPlywoodCategoryMockMvc.perform(put("/api/m-plywood-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywoodCategory)))
            .andExpect(status().isBadRequest());

        // Validate the MPlywoodCategory in the database
        List<MPlywoodCategory> mPlywoodCategoryList = mPlywoodCategoryRepository.findAll();
        assertThat(mPlywoodCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPlywoodCategory() throws Exception {
        // Initialize the database
        mPlywoodCategoryService.save(mPlywoodCategory);

        int databaseSizeBeforeDelete = mPlywoodCategoryRepository.findAll().size();

        // Get the mPlywoodCategory
        restMPlywoodCategoryMockMvc.perform(delete("/api/m-plywood-categories/{id}", mPlywoodCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MPlywoodCategory> mPlywoodCategoryList = mPlywoodCategoryRepository.findAll();
        assertThat(mPlywoodCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPlywoodCategory.class);
        MPlywoodCategory mPlywoodCategory1 = new MPlywoodCategory();
        mPlywoodCategory1.setId(1L);
        MPlywoodCategory mPlywoodCategory2 = new MPlywoodCategory();
        mPlywoodCategory2.setId(mPlywoodCategory1.getId());
        assertThat(mPlywoodCategory1).isEqualTo(mPlywoodCategory2);
        mPlywoodCategory2.setId(2L);
        assertThat(mPlywoodCategory1).isNotEqualTo(mPlywoodCategory2);
        mPlywoodCategory1.setId(null);
        assertThat(mPlywoodCategory1).isNotEqualTo(mPlywoodCategory2);
    }
}

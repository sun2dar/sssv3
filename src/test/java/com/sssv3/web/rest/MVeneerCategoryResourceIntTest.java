package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MVeneerCategory;
import com.sssv3.repository.MVeneerCategoryRepository;
import com.sssv3.service.MVeneerCategoryService;
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

import com.sssv3.domain.enumeration.VeneerType;
import com.sssv3.domain.enumeration.VeneerSubType;
import com.sssv3.domain.enumeration.Status;
/**
 * Test class for the MVeneerCategoryResource REST controller.
 *
 * @see MVeneerCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MVeneerCategoryResourceIntTest {

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

    private static final VeneerType DEFAULT_TYPE = VeneerType.BASAH;
    private static final VeneerType UPDATED_TYPE = VeneerType.KERING;

    private static final VeneerSubType DEFAULT_SUBTYPE = VeneerSubType.FB;
    private static final VeneerSubType UPDATED_SUBTYPE = VeneerSubType.LC;

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MVeneerCategoryRepository mVeneerCategoryRepository;

    @Autowired
    private MVeneerCategoryService mVeneerCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMVeneerCategoryMockMvc;

    private MVeneerCategory mVeneerCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MVeneerCategoryResource mVeneerCategoryResource = new MVeneerCategoryResource(mVeneerCategoryService);
        this.restMVeneerCategoryMockMvc = MockMvcBuilders.standaloneSetup(mVeneerCategoryResource)
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
    public static MVeneerCategory createEntity(EntityManager em) {
        MVeneerCategory mVeneerCategory = new MVeneerCategory()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .tebal(DEFAULT_TEBAL)
            .panjang(DEFAULT_PANJANG)
            .lebar(DEFAULT_LEBAR)
            .hargaBeli(DEFAULT_HARGA_BELI)
            .hargaJual(DEFAULT_HARGA_JUAL)
            .type(DEFAULT_TYPE)
            .subtype(DEFAULT_SUBTYPE)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mVeneerCategory;
    }

    @Before
    public void initTest() {
        mVeneerCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVeneerCategory() throws Exception {
        int databaseSizeBeforeCreate = mVeneerCategoryRepository.findAll().size();

        // Create the MVeneerCategory
        restMVeneerCategoryMockMvc.perform(post("/api/m-veneer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mVeneerCategory)))
            .andExpect(status().isCreated());

        // Validate the MVeneerCategory in the database
        List<MVeneerCategory> mVeneerCategoryList = mVeneerCategoryRepository.findAll();
        assertThat(mVeneerCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        MVeneerCategory testMVeneerCategory = mVeneerCategoryList.get(mVeneerCategoryList.size() - 1);
        assertThat(testMVeneerCategory.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMVeneerCategory.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMVeneerCategory.getTebal()).isEqualTo(DEFAULT_TEBAL);
        assertThat(testMVeneerCategory.getPanjang()).isEqualTo(DEFAULT_PANJANG);
        assertThat(testMVeneerCategory.getLebar()).isEqualTo(DEFAULT_LEBAR);
        assertThat(testMVeneerCategory.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testMVeneerCategory.getHargaJual()).isEqualTo(DEFAULT_HARGA_JUAL);
        assertThat(testMVeneerCategory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMVeneerCategory.getSubtype()).isEqualTo(DEFAULT_SUBTYPE);
        assertThat(testMVeneerCategory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMVeneerCategory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMVeneerCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVeneerCategoryRepository.findAll().size();

        // Create the MVeneerCategory with an existing ID
        mVeneerCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVeneerCategoryMockMvc.perform(post("/api/m-veneer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mVeneerCategory)))
            .andExpect(status().isBadRequest());

        // Validate the MVeneerCategory in the database
        List<MVeneerCategory> mVeneerCategoryList = mVeneerCategoryRepository.findAll();
        assertThat(mVeneerCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mVeneerCategoryRepository.findAll().size();
        // set the field null
        mVeneerCategory.setNama(null);

        // Create the MVeneerCategory, which fails.

        restMVeneerCategoryMockMvc.perform(post("/api/m-veneer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mVeneerCategory)))
            .andExpect(status().isBadRequest());

        List<MVeneerCategory> mVeneerCategoryList = mVeneerCategoryRepository.findAll();
        assertThat(mVeneerCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMVeneerCategories() throws Exception {
        // Initialize the database
        mVeneerCategoryRepository.saveAndFlush(mVeneerCategory);

        // Get all the mVeneerCategoryList
        restMVeneerCategoryMockMvc.perform(get("/api/m-veneer-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVeneerCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].tebal").value(hasItem(DEFAULT_TEBAL.doubleValue())))
            .andExpect(jsonPath("$.[*].panjang").value(hasItem(DEFAULT_PANJANG.doubleValue())))
            .andExpect(jsonPath("$.[*].lebar").value(hasItem(DEFAULT_LEBAR.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaJual").value(hasItem(DEFAULT_HARGA_JUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].subtype").value(hasItem(DEFAULT_SUBTYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMVeneerCategory() throws Exception {
        // Initialize the database
        mVeneerCategoryRepository.saveAndFlush(mVeneerCategory);

        // Get the mVeneerCategory
        restMVeneerCategoryMockMvc.perform(get("/api/m-veneer-categories/{id}", mVeneerCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mVeneerCategory.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.tebal").value(DEFAULT_TEBAL.doubleValue()))
            .andExpect(jsonPath("$.panjang").value(DEFAULT_PANJANG.doubleValue()))
            .andExpect(jsonPath("$.lebar").value(DEFAULT_LEBAR.doubleValue()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.doubleValue()))
            .andExpect(jsonPath("$.hargaJual").value(DEFAULT_HARGA_JUAL.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.subtype").value(DEFAULT_SUBTYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMVeneerCategory() throws Exception {
        // Get the mVeneerCategory
        restMVeneerCategoryMockMvc.perform(get("/api/m-veneer-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVeneerCategory() throws Exception {
        // Initialize the database
        mVeneerCategoryService.save(mVeneerCategory);

        int databaseSizeBeforeUpdate = mVeneerCategoryRepository.findAll().size();

        // Update the mVeneerCategory
        MVeneerCategory updatedMVeneerCategory = mVeneerCategoryRepository.findById(mVeneerCategory.getId()).get();
        // Disconnect from session so that the updates on updatedMVeneerCategory are not directly saved in db
        em.detach(updatedMVeneerCategory);
        updatedMVeneerCategory
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .tebal(UPDATED_TEBAL)
            .panjang(UPDATED_PANJANG)
            .lebar(UPDATED_LEBAR)
            .hargaBeli(UPDATED_HARGA_BELI)
            .hargaJual(UPDATED_HARGA_JUAL)
            .type(UPDATED_TYPE)
            .subtype(UPDATED_SUBTYPE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMVeneerCategoryMockMvc.perform(put("/api/m-veneer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMVeneerCategory)))
            .andExpect(status().isOk());

        // Validate the MVeneerCategory in the database
        List<MVeneerCategory> mVeneerCategoryList = mVeneerCategoryRepository.findAll();
        assertThat(mVeneerCategoryList).hasSize(databaseSizeBeforeUpdate);
        MVeneerCategory testMVeneerCategory = mVeneerCategoryList.get(mVeneerCategoryList.size() - 1);
        assertThat(testMVeneerCategory.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMVeneerCategory.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMVeneerCategory.getTebal()).isEqualTo(UPDATED_TEBAL);
        assertThat(testMVeneerCategory.getPanjang()).isEqualTo(UPDATED_PANJANG);
        assertThat(testMVeneerCategory.getLebar()).isEqualTo(UPDATED_LEBAR);
        assertThat(testMVeneerCategory.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testMVeneerCategory.getHargaJual()).isEqualTo(UPDATED_HARGA_JUAL);
        assertThat(testMVeneerCategory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMVeneerCategory.getSubtype()).isEqualTo(UPDATED_SUBTYPE);
        assertThat(testMVeneerCategory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMVeneerCategory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMVeneerCategory() throws Exception {
        int databaseSizeBeforeUpdate = mVeneerCategoryRepository.findAll().size();

        // Create the MVeneerCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVeneerCategoryMockMvc.perform(put("/api/m-veneer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mVeneerCategory)))
            .andExpect(status().isBadRequest());

        // Validate the MVeneerCategory in the database
        List<MVeneerCategory> mVeneerCategoryList = mVeneerCategoryRepository.findAll();
        assertThat(mVeneerCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVeneerCategory() throws Exception {
        // Initialize the database
        mVeneerCategoryService.save(mVeneerCategory);

        int databaseSizeBeforeDelete = mVeneerCategoryRepository.findAll().size();

        // Get the mVeneerCategory
        restMVeneerCategoryMockMvc.perform(delete("/api/m-veneer-categories/{id}", mVeneerCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MVeneerCategory> mVeneerCategoryList = mVeneerCategoryRepository.findAll();
        assertThat(mVeneerCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVeneerCategory.class);
        MVeneerCategory mVeneerCategory1 = new MVeneerCategory();
        mVeneerCategory1.setId(1L);
        MVeneerCategory mVeneerCategory2 = new MVeneerCategory();
        mVeneerCategory2.setId(mVeneerCategory1.getId());
        assertThat(mVeneerCategory1).isEqualTo(mVeneerCategory2);
        mVeneerCategory2.setId(2L);
        assertThat(mVeneerCategory1).isNotEqualTo(mVeneerCategory2);
        mVeneerCategory1.setId(null);
        assertThat(mVeneerCategory1).isNotEqualTo(mVeneerCategory2);
    }
}

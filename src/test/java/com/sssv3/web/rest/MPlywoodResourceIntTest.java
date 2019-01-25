package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MPlywood;
import com.sssv3.repository.MPlywoodRepository;
import com.sssv3.service.MPlywoodService;
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
 * Test class for the MPlywoodResource REST controller.
 *
 * @see MPlywoodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MPlywoodResourceIntTest {

    private static final Double DEFAULT_HARGA_BELI = 1D;
    private static final Double UPDATED_HARGA_BELI = 2D;

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;

    private static final Double DEFAULT_QTY_PRODUKSI = 1D;
    private static final Double UPDATED_QTY_PRODUKSI = 2D;

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MPlywoodRepository mPlywoodRepository;

    @Autowired
    private MPlywoodService mPlywoodService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMPlywoodMockMvc;

    private MPlywood mPlywood;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MPlywoodResource mPlywoodResource = new MPlywoodResource(mPlywoodService);
        this.restMPlywoodMockMvc = MockMvcBuilders.standaloneSetup(mPlywoodResource)
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
    public static MPlywood createEntity(EntityManager em) {
        MPlywood mPlywood = new MPlywood()
            .hargaBeli(DEFAULT_HARGA_BELI)
            .qty(DEFAULT_QTY)
            .qtyProduksi(DEFAULT_QTY_PRODUKSI)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mPlywood;
    }

    @Before
    public void initTest() {
        mPlywood = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPlywood() throws Exception {
        int databaseSizeBeforeCreate = mPlywoodRepository.findAll().size();

        // Create the MPlywood
        restMPlywoodMockMvc.perform(post("/api/m-plywoods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywood)))
            .andExpect(status().isCreated());

        // Validate the MPlywood in the database
        List<MPlywood> mPlywoodList = mPlywoodRepository.findAll();
        assertThat(mPlywoodList).hasSize(databaseSizeBeforeCreate + 1);
        MPlywood testMPlywood = mPlywoodList.get(mPlywoodList.size() - 1);
        assertThat(testMPlywood.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testMPlywood.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testMPlywood.getQtyProduksi()).isEqualTo(DEFAULT_QTY_PRODUKSI);
        assertThat(testMPlywood.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMPlywood.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMPlywoodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPlywoodRepository.findAll().size();

        // Create the MPlywood with an existing ID
        mPlywood.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPlywoodMockMvc.perform(post("/api/m-plywoods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywood)))
            .andExpect(status().isBadRequest());

        // Validate the MPlywood in the database
        List<MPlywood> mPlywoodList = mPlywoodRepository.findAll();
        assertThat(mPlywoodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMPlywoods() throws Exception {
        // Initialize the database
        mPlywoodRepository.saveAndFlush(mPlywood);

        // Get all the mPlywoodList
        restMPlywoodMockMvc.perform(get("/api/m-plywoods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPlywood.getId().intValue())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.doubleValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyProduksi").value(hasItem(DEFAULT_QTY_PRODUKSI.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMPlywood() throws Exception {
        // Initialize the database
        mPlywoodRepository.saveAndFlush(mPlywood);

        // Get the mPlywood
        restMPlywoodMockMvc.perform(get("/api/m-plywoods/{id}", mPlywood.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mPlywood.getId().intValue()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.doubleValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.qtyProduksi").value(DEFAULT_QTY_PRODUKSI.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMPlywood() throws Exception {
        // Get the mPlywood
        restMPlywoodMockMvc.perform(get("/api/m-plywoods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPlywood() throws Exception {
        // Initialize the database
        mPlywoodService.save(mPlywood);

        int databaseSizeBeforeUpdate = mPlywoodRepository.findAll().size();

        // Update the mPlywood
        MPlywood updatedMPlywood = mPlywoodRepository.findById(mPlywood.getId()).get();
        // Disconnect from session so that the updates on updatedMPlywood are not directly saved in db
        em.detach(updatedMPlywood);
        updatedMPlywood
            .hargaBeli(UPDATED_HARGA_BELI)
            .qty(UPDATED_QTY)
            .qtyProduksi(UPDATED_QTY_PRODUKSI)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMPlywoodMockMvc.perform(put("/api/m-plywoods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMPlywood)))
            .andExpect(status().isOk());

        // Validate the MPlywood in the database
        List<MPlywood> mPlywoodList = mPlywoodRepository.findAll();
        assertThat(mPlywoodList).hasSize(databaseSizeBeforeUpdate);
        MPlywood testMPlywood = mPlywoodList.get(mPlywoodList.size() - 1);
        assertThat(testMPlywood.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testMPlywood.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testMPlywood.getQtyProduksi()).isEqualTo(UPDATED_QTY_PRODUKSI);
        assertThat(testMPlywood.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMPlywood.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMPlywood() throws Exception {
        int databaseSizeBeforeUpdate = mPlywoodRepository.findAll().size();

        // Create the MPlywood

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPlywoodMockMvc.perform(put("/api/m-plywoods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywood)))
            .andExpect(status().isBadRequest());

        // Validate the MPlywood in the database
        List<MPlywood> mPlywoodList = mPlywoodRepository.findAll();
        assertThat(mPlywoodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPlywood() throws Exception {
        // Initialize the database
        mPlywoodService.save(mPlywood);

        int databaseSizeBeforeDelete = mPlywoodRepository.findAll().size();

        // Get the mPlywood
        restMPlywoodMockMvc.perform(delete("/api/m-plywoods/{id}", mPlywood.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MPlywood> mPlywoodList = mPlywoodRepository.findAll();
        assertThat(mPlywoodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPlywood.class);
        MPlywood mPlywood1 = new MPlywood();
        mPlywood1.setId(1L);
        MPlywood mPlywood2 = new MPlywood();
        mPlywood2.setId(mPlywood1.getId());
        assertThat(mPlywood1).isEqualTo(mPlywood2);
        mPlywood2.setId(2L);
        assertThat(mPlywood1).isNotEqualTo(mPlywood2);
        mPlywood1.setId(null);
        assertThat(mPlywood1).isNotEqualTo(mPlywood2);
    }
}

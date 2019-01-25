package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MVeneer;
import com.sssv3.repository.MVeneerRepository;
import com.sssv3.service.MVeneerService;
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
 * Test class for the MVeneerResource REST controller.
 *
 * @see MVeneerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MVeneerResourceIntTest {

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
    private MVeneerRepository mVeneerRepository;

    @Autowired
    private MVeneerService mVeneerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMVeneerMockMvc;

    private MVeneer mVeneer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MVeneerResource mVeneerResource = new MVeneerResource(mVeneerService);
        this.restMVeneerMockMvc = MockMvcBuilders.standaloneSetup(mVeneerResource)
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
    public static MVeneer createEntity(EntityManager em) {
        MVeneer mVeneer = new MVeneer()
            .hargaBeli(DEFAULT_HARGA_BELI)
            .qty(DEFAULT_QTY)
            .qtyProduksi(DEFAULT_QTY_PRODUKSI)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mVeneer;
    }

    @Before
    public void initTest() {
        mVeneer = createEntity(em);
    }

    @Test
    @Transactional
    public void createMVeneer() throws Exception {
        int databaseSizeBeforeCreate = mVeneerRepository.findAll().size();

        // Create the MVeneer
        restMVeneerMockMvc.perform(post("/api/m-veneers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mVeneer)))
            .andExpect(status().isCreated());

        // Validate the MVeneer in the database
        List<MVeneer> mVeneerList = mVeneerRepository.findAll();
        assertThat(mVeneerList).hasSize(databaseSizeBeforeCreate + 1);
        MVeneer testMVeneer = mVeneerList.get(mVeneerList.size() - 1);
        assertThat(testMVeneer.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testMVeneer.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testMVeneer.getQtyProduksi()).isEqualTo(DEFAULT_QTY_PRODUKSI);
        assertThat(testMVeneer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMVeneer.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMVeneerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mVeneerRepository.findAll().size();

        // Create the MVeneer with an existing ID
        mVeneer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMVeneerMockMvc.perform(post("/api/m-veneers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mVeneer)))
            .andExpect(status().isBadRequest());

        // Validate the MVeneer in the database
        List<MVeneer> mVeneerList = mVeneerRepository.findAll();
        assertThat(mVeneerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMVeneers() throws Exception {
        // Initialize the database
        mVeneerRepository.saveAndFlush(mVeneer);

        // Get all the mVeneerList
        restMVeneerMockMvc.perform(get("/api/m-veneers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVeneer.getId().intValue())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.doubleValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyProduksi").value(hasItem(DEFAULT_QTY_PRODUKSI.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMVeneer() throws Exception {
        // Initialize the database
        mVeneerRepository.saveAndFlush(mVeneer);

        // Get the mVeneer
        restMVeneerMockMvc.perform(get("/api/m-veneers/{id}", mVeneer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mVeneer.getId().intValue()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.doubleValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.qtyProduksi").value(DEFAULT_QTY_PRODUKSI.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMVeneer() throws Exception {
        // Get the mVeneer
        restMVeneerMockMvc.perform(get("/api/m-veneers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMVeneer() throws Exception {
        // Initialize the database
        mVeneerService.save(mVeneer);

        int databaseSizeBeforeUpdate = mVeneerRepository.findAll().size();

        // Update the mVeneer
        MVeneer updatedMVeneer = mVeneerRepository.findById(mVeneer.getId()).get();
        // Disconnect from session so that the updates on updatedMVeneer are not directly saved in db
        em.detach(updatedMVeneer);
        updatedMVeneer
            .hargaBeli(UPDATED_HARGA_BELI)
            .qty(UPDATED_QTY)
            .qtyProduksi(UPDATED_QTY_PRODUKSI)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMVeneerMockMvc.perform(put("/api/m-veneers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMVeneer)))
            .andExpect(status().isOk());

        // Validate the MVeneer in the database
        List<MVeneer> mVeneerList = mVeneerRepository.findAll();
        assertThat(mVeneerList).hasSize(databaseSizeBeforeUpdate);
        MVeneer testMVeneer = mVeneerList.get(mVeneerList.size() - 1);
        assertThat(testMVeneer.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testMVeneer.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testMVeneer.getQtyProduksi()).isEqualTo(UPDATED_QTY_PRODUKSI);
        assertThat(testMVeneer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMVeneer.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMVeneer() throws Exception {
        int databaseSizeBeforeUpdate = mVeneerRepository.findAll().size();

        // Create the MVeneer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMVeneerMockMvc.perform(put("/api/m-veneers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mVeneer)))
            .andExpect(status().isBadRequest());

        // Validate the MVeneer in the database
        List<MVeneer> mVeneerList = mVeneerRepository.findAll();
        assertThat(mVeneerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMVeneer() throws Exception {
        // Initialize the database
        mVeneerService.save(mVeneer);

        int databaseSizeBeforeDelete = mVeneerRepository.findAll().size();

        // Get the mVeneer
        restMVeneerMockMvc.perform(delete("/api/m-veneers/{id}", mVeneer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MVeneer> mVeneerList = mVeneerRepository.findAll();
        assertThat(mVeneerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVeneer.class);
        MVeneer mVeneer1 = new MVeneer();
        mVeneer1.setId(1L);
        MVeneer mVeneer2 = new MVeneer();
        mVeneer2.setId(mVeneer1.getId());
        assertThat(mVeneer1).isEqualTo(mVeneer2);
        mVeneer2.setId(2L);
        assertThat(mVeneer1).isNotEqualTo(mVeneer2);
        mVeneer1.setId(null);
        assertThat(mVeneer1).isNotEqualTo(mVeneer2);
    }
}

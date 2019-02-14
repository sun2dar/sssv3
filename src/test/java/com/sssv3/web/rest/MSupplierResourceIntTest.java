package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MSupplier;
import com.sssv3.repository.MSupplierRepository;
import com.sssv3.service.MSupplierService;
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
 * Test class for the MSupplierResource REST controller.
 *
 * @see MSupplierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MSupplierResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPON = "AAAAAAAAAA";
    private static final String UPDATED_TELEPON = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ALAMAT = "AAAAAAAAAA";
    private static final String UPDATED_ALAMAT = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MSupplierRepository mSupplierRepository;

    @Autowired
    private MSupplierService mSupplierService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMSupplierMockMvc;

    private MSupplier mSupplier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MSupplierResource mSupplierResource = new MSupplierResource(mSupplierService);
        this.restMSupplierMockMvc = MockMvcBuilders.standaloneSetup(mSupplierResource)
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
    public static MSupplier createEntity(EntityManager em) {
        MSupplier mSupplier = new MSupplier()
            .nama(DEFAULT_NAMA)
            .telepon(DEFAULT_TELEPON)
            .mobilephone(DEFAULT_MOBILEPHONE)
            .alamat(DEFAULT_ALAMAT)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mSupplier;
    }

    @Before
    public void initTest() {
        mSupplier = createEntity(em);
    }

    @Test
    @Transactional
    public void createMSupplier() throws Exception {
        int databaseSizeBeforeCreate = mSupplierRepository.findAll().size();

        // Create the MSupplier
        restMSupplierMockMvc.perform(post("/api/m-suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mSupplier)))
            .andExpect(status().isCreated());

        // Validate the MSupplier in the database
        List<MSupplier> mSupplierList = mSupplierRepository.findAll();
        assertThat(mSupplierList).hasSize(databaseSizeBeforeCreate + 1);
        MSupplier testMSupplier = mSupplierList.get(mSupplierList.size() - 1);
        assertThat(testMSupplier.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMSupplier.getTelepon()).isEqualTo(DEFAULT_TELEPON);
        assertThat(testMSupplier.getMobilephone()).isEqualTo(DEFAULT_MOBILEPHONE);
        assertThat(testMSupplier.getAlamat()).isEqualTo(DEFAULT_ALAMAT);
        assertThat(testMSupplier.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMSupplier.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMSupplierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mSupplierRepository.findAll().size();

        // Create the MSupplier with an existing ID
        mSupplier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMSupplierMockMvc.perform(post("/api/m-suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mSupplier)))
            .andExpect(status().isBadRequest());

        // Validate the MSupplier in the database
        List<MSupplier> mSupplierList = mSupplierRepository.findAll();
        assertThat(mSupplierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mSupplierRepository.findAll().size();
        // set the field null
        mSupplier.setNama(null);

        // Create the MSupplier, which fails.

        restMSupplierMockMvc.perform(post("/api/m-suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mSupplier)))
            .andExpect(status().isBadRequest());

        List<MSupplier> mSupplierList = mSupplierRepository.findAll();
        assertThat(mSupplierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMSuppliers() throws Exception {
        // Initialize the database
        mSupplierRepository.saveAndFlush(mSupplier);

        // Get all the mSupplierList
        restMSupplierMockMvc.perform(get("/api/m-suppliers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mSupplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].telepon").value(hasItem(DEFAULT_TELEPON.toString())))
            .andExpect(jsonPath("$.[*].mobilephone").value(hasItem(DEFAULT_MOBILEPHONE.toString())))
            .andExpect(jsonPath("$.[*].alamat").value(hasItem(DEFAULT_ALAMAT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMSupplier() throws Exception {
        // Initialize the database
        mSupplierRepository.saveAndFlush(mSupplier);

        // Get the mSupplier
        restMSupplierMockMvc.perform(get("/api/m-suppliers/{id}", mSupplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mSupplier.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.telepon").value(DEFAULT_TELEPON.toString()))
            .andExpect(jsonPath("$.mobilephone").value(DEFAULT_MOBILEPHONE.toString()))
            .andExpect(jsonPath("$.alamat").value(DEFAULT_ALAMAT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMSupplier() throws Exception {
        // Get the mSupplier
        restMSupplierMockMvc.perform(get("/api/m-suppliers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMSupplier() throws Exception {
        // Initialize the database
        mSupplierService.save(mSupplier);

        int databaseSizeBeforeUpdate = mSupplierRepository.findAll().size();

        // Update the mSupplier
        MSupplier updatedMSupplier = mSupplierRepository.findById(mSupplier.getId()).get();
        // Disconnect from session so that the updates on updatedMSupplier are not directly saved in db
        em.detach(updatedMSupplier);
        updatedMSupplier
            .nama(UPDATED_NAMA)
            .telepon(UPDATED_TELEPON)
            .mobilephone(UPDATED_MOBILEPHONE)
            .alamat(UPDATED_ALAMAT)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMSupplierMockMvc.perform(put("/api/m-suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMSupplier)))
            .andExpect(status().isOk());

        // Validate the MSupplier in the database
        List<MSupplier> mSupplierList = mSupplierRepository.findAll();
        assertThat(mSupplierList).hasSize(databaseSizeBeforeUpdate);
        MSupplier testMSupplier = mSupplierList.get(mSupplierList.size() - 1);
        assertThat(testMSupplier.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMSupplier.getTelepon()).isEqualTo(UPDATED_TELEPON);
        assertThat(testMSupplier.getMobilephone()).isEqualTo(UPDATED_MOBILEPHONE);
        assertThat(testMSupplier.getAlamat()).isEqualTo(UPDATED_ALAMAT);
        assertThat(testMSupplier.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMSupplier.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMSupplier() throws Exception {
        int databaseSizeBeforeUpdate = mSupplierRepository.findAll().size();

        // Create the MSupplier

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMSupplierMockMvc.perform(put("/api/m-suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mSupplier)))
            .andExpect(status().isBadRequest());

        // Validate the MSupplier in the database
        List<MSupplier> mSupplierList = mSupplierRepository.findAll();
        assertThat(mSupplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMSupplier() throws Exception {
        // Initialize the database
        mSupplierService.save(mSupplier);

        int databaseSizeBeforeDelete = mSupplierRepository.findAll().size();

        // Get the mSupplier
        restMSupplierMockMvc.perform(delete("/api/m-suppliers/{id}", mSupplier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MSupplier> mSupplierList = mSupplierRepository.findAll();
        assertThat(mSupplierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MSupplier.class);
        MSupplier mSupplier1 = new MSupplier();
        mSupplier1.setId(1L);
        MSupplier mSupplier2 = new MSupplier();
        mSupplier2.setId(mSupplier1.getId());
        assertThat(mSupplier1).isEqualTo(mSupplier2);
        mSupplier2.setId(2L);
        assertThat(mSupplier1).isNotEqualTo(mSupplier2);
        mSupplier1.setId(null);
        assertThat(mSupplier1).isNotEqualTo(mSupplier2);
    }
}

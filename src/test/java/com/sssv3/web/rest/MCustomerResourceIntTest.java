package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MCustomer;
import com.sssv3.repository.MCustomerRepository;
import com.sssv3.service.MCustomerService;
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
 * Test class for the MCustomerResource REST controller.
 *
 * @see MCustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MCustomerResourceIntTest {

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
    private MCustomerRepository mCustomerRepository;

    @Autowired
    private MCustomerService mCustomerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMCustomerMockMvc;

    private MCustomer mCustomer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MCustomerResource mCustomerResource = new MCustomerResource(mCustomerService);
        this.restMCustomerMockMvc = MockMvcBuilders.standaloneSetup(mCustomerResource)
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
    public static MCustomer createEntity(EntityManager em) {
        MCustomer mCustomer = new MCustomer()
            .nama(DEFAULT_NAMA)
            .telepon(DEFAULT_TELEPON)
            .mobilephone(DEFAULT_MOBILEPHONE)
            .alamat(DEFAULT_ALAMAT)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mCustomer;
    }

    @Before
    public void initTest() {
        mCustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createMCustomer() throws Exception {
        int databaseSizeBeforeCreate = mCustomerRepository.findAll().size();

        // Create the MCustomer
        restMCustomerMockMvc.perform(post("/api/m-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mCustomer)))
            .andExpect(status().isCreated());

        // Validate the MCustomer in the database
        List<MCustomer> mCustomerList = mCustomerRepository.findAll();
        assertThat(mCustomerList).hasSize(databaseSizeBeforeCreate + 1);
        MCustomer testMCustomer = mCustomerList.get(mCustomerList.size() - 1);
        assertThat(testMCustomer.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMCustomer.getTelepon()).isEqualTo(DEFAULT_TELEPON);
        assertThat(testMCustomer.getMobilephone()).isEqualTo(DEFAULT_MOBILEPHONE);
        assertThat(testMCustomer.getAlamat()).isEqualTo(DEFAULT_ALAMAT);
        assertThat(testMCustomer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMCustomer.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mCustomerRepository.findAll().size();

        // Create the MCustomer with an existing ID
        mCustomer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMCustomerMockMvc.perform(post("/api/m-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mCustomer)))
            .andExpect(status().isBadRequest());

        // Validate the MCustomer in the database
        List<MCustomer> mCustomerList = mCustomerRepository.findAll();
        assertThat(mCustomerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mCustomerRepository.findAll().size();
        // set the field null
        mCustomer.setNama(null);

        // Create the MCustomer, which fails.

        restMCustomerMockMvc.perform(post("/api/m-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mCustomer)))
            .andExpect(status().isBadRequest());

        List<MCustomer> mCustomerList = mCustomerRepository.findAll();
        assertThat(mCustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMCustomers() throws Exception {
        // Initialize the database
        mCustomerRepository.saveAndFlush(mCustomer);

        // Get all the mCustomerList
        restMCustomerMockMvc.perform(get("/api/m-customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mCustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].telepon").value(hasItem(DEFAULT_TELEPON.toString())))
            .andExpect(jsonPath("$.[*].mobilephone").value(hasItem(DEFAULT_MOBILEPHONE.toString())))
            .andExpect(jsonPath("$.[*].alamat").value(hasItem(DEFAULT_ALAMAT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMCustomer() throws Exception {
        // Initialize the database
        mCustomerRepository.saveAndFlush(mCustomer);

        // Get the mCustomer
        restMCustomerMockMvc.perform(get("/api/m-customers/{id}", mCustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mCustomer.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.telepon").value(DEFAULT_TELEPON.toString()))
            .andExpect(jsonPath("$.mobilephone").value(DEFAULT_MOBILEPHONE.toString()))
            .andExpect(jsonPath("$.alamat").value(DEFAULT_ALAMAT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMCustomer() throws Exception {
        // Get the mCustomer
        restMCustomerMockMvc.perform(get("/api/m-customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMCustomer() throws Exception {
        // Initialize the database
        mCustomerService.save(mCustomer);

        int databaseSizeBeforeUpdate = mCustomerRepository.findAll().size();

        // Update the mCustomer
        MCustomer updatedMCustomer = mCustomerRepository.findById(mCustomer.getId()).get();
        // Disconnect from session so that the updates on updatedMCustomer are not directly saved in db
        em.detach(updatedMCustomer);
        updatedMCustomer
            .nama(UPDATED_NAMA)
            .telepon(UPDATED_TELEPON)
            .mobilephone(UPDATED_MOBILEPHONE)
            .alamat(UPDATED_ALAMAT)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMCustomerMockMvc.perform(put("/api/m-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMCustomer)))
            .andExpect(status().isOk());

        // Validate the MCustomer in the database
        List<MCustomer> mCustomerList = mCustomerRepository.findAll();
        assertThat(mCustomerList).hasSize(databaseSizeBeforeUpdate);
        MCustomer testMCustomer = mCustomerList.get(mCustomerList.size() - 1);
        assertThat(testMCustomer.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMCustomer.getTelepon()).isEqualTo(UPDATED_TELEPON);
        assertThat(testMCustomer.getMobilephone()).isEqualTo(UPDATED_MOBILEPHONE);
        assertThat(testMCustomer.getAlamat()).isEqualTo(UPDATED_ALAMAT);
        assertThat(testMCustomer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMCustomer.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMCustomer() throws Exception {
        int databaseSizeBeforeUpdate = mCustomerRepository.findAll().size();

        // Create the MCustomer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMCustomerMockMvc.perform(put("/api/m-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mCustomer)))
            .andExpect(status().isBadRequest());

        // Validate the MCustomer in the database
        List<MCustomer> mCustomerList = mCustomerRepository.findAll();
        assertThat(mCustomerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMCustomer() throws Exception {
        // Initialize the database
        mCustomerService.save(mCustomer);

        int databaseSizeBeforeDelete = mCustomerRepository.findAll().size();

        // Get the mCustomer
        restMCustomerMockMvc.perform(delete("/api/m-customers/{id}", mCustomer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MCustomer> mCustomerList = mCustomerRepository.findAll();
        assertThat(mCustomerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MCustomer.class);
        MCustomer mCustomer1 = new MCustomer();
        mCustomer1.setId(1L);
        MCustomer mCustomer2 = new MCustomer();
        mCustomer2.setId(mCustomer1.getId());
        assertThat(mCustomer1).isEqualTo(mCustomer2);
        mCustomer2.setId(2L);
        assertThat(mCustomer1).isNotEqualTo(mCustomer2);
        mCustomer1.setId(null);
        assertThat(mCustomer1).isNotEqualTo(mCustomer2);
    }
}

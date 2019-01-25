package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MPaytype;
import com.sssv3.repository.MPaytypeRepository;
import com.sssv3.service.MPaytypeService;
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
 * Test class for the MPaytypeResource REST controller.
 *
 * @see MPaytypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MPaytypeResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MPaytypeRepository mPaytypeRepository;

    @Autowired
    private MPaytypeService mPaytypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMPaytypeMockMvc;

    private MPaytype mPaytype;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MPaytypeResource mPaytypeResource = new MPaytypeResource(mPaytypeService);
        this.restMPaytypeMockMvc = MockMvcBuilders.standaloneSetup(mPaytypeResource)
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
    public static MPaytype createEntity(EntityManager em) {
        MPaytype mPaytype = new MPaytype()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mPaytype;
    }

    @Before
    public void initTest() {
        mPaytype = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPaytype() throws Exception {
        int databaseSizeBeforeCreate = mPaytypeRepository.findAll().size();

        // Create the MPaytype
        restMPaytypeMockMvc.perform(post("/api/m-paytypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPaytype)))
            .andExpect(status().isCreated());

        // Validate the MPaytype in the database
        List<MPaytype> mPaytypeList = mPaytypeRepository.findAll();
        assertThat(mPaytypeList).hasSize(databaseSizeBeforeCreate + 1);
        MPaytype testMPaytype = mPaytypeList.get(mPaytypeList.size() - 1);
        assertThat(testMPaytype.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMPaytype.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMPaytype.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMPaytype.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMPaytypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPaytypeRepository.findAll().size();

        // Create the MPaytype with an existing ID
        mPaytype.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPaytypeMockMvc.perform(post("/api/m-paytypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPaytype)))
            .andExpect(status().isBadRequest());

        // Validate the MPaytype in the database
        List<MPaytype> mPaytypeList = mPaytypeRepository.findAll();
        assertThat(mPaytypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPaytypeRepository.findAll().size();
        // set the field null
        mPaytype.setNama(null);

        // Create the MPaytype, which fails.

        restMPaytypeMockMvc.perform(post("/api/m-paytypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPaytype)))
            .andExpect(status().isBadRequest());

        List<MPaytype> mPaytypeList = mPaytypeRepository.findAll();
        assertThat(mPaytypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMPaytypes() throws Exception {
        // Initialize the database
        mPaytypeRepository.saveAndFlush(mPaytype);

        // Get all the mPaytypeList
        restMPaytypeMockMvc.perform(get("/api/m-paytypes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPaytype.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMPaytype() throws Exception {
        // Initialize the database
        mPaytypeRepository.saveAndFlush(mPaytype);

        // Get the mPaytype
        restMPaytypeMockMvc.perform(get("/api/m-paytypes/{id}", mPaytype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mPaytype.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMPaytype() throws Exception {
        // Get the mPaytype
        restMPaytypeMockMvc.perform(get("/api/m-paytypes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPaytype() throws Exception {
        // Initialize the database
        mPaytypeService.save(mPaytype);

        int databaseSizeBeforeUpdate = mPaytypeRepository.findAll().size();

        // Update the mPaytype
        MPaytype updatedMPaytype = mPaytypeRepository.findById(mPaytype.getId()).get();
        // Disconnect from session so that the updates on updatedMPaytype are not directly saved in db
        em.detach(updatedMPaytype);
        updatedMPaytype
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMPaytypeMockMvc.perform(put("/api/m-paytypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMPaytype)))
            .andExpect(status().isOk());

        // Validate the MPaytype in the database
        List<MPaytype> mPaytypeList = mPaytypeRepository.findAll();
        assertThat(mPaytypeList).hasSize(databaseSizeBeforeUpdate);
        MPaytype testMPaytype = mPaytypeList.get(mPaytypeList.size() - 1);
        assertThat(testMPaytype.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMPaytype.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMPaytype.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMPaytype.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMPaytype() throws Exception {
        int databaseSizeBeforeUpdate = mPaytypeRepository.findAll().size();

        // Create the MPaytype

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPaytypeMockMvc.perform(put("/api/m-paytypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPaytype)))
            .andExpect(status().isBadRequest());

        // Validate the MPaytype in the database
        List<MPaytype> mPaytypeList = mPaytypeRepository.findAll();
        assertThat(mPaytypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPaytype() throws Exception {
        // Initialize the database
        mPaytypeService.save(mPaytype);

        int databaseSizeBeforeDelete = mPaytypeRepository.findAll().size();

        // Get the mPaytype
        restMPaytypeMockMvc.perform(delete("/api/m-paytypes/{id}", mPaytype.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MPaytype> mPaytypeList = mPaytypeRepository.findAll();
        assertThat(mPaytypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPaytype.class);
        MPaytype mPaytype1 = new MPaytype();
        mPaytype1.setId(1L);
        MPaytype mPaytype2 = new MPaytype();
        mPaytype2.setId(mPaytype1.getId());
        assertThat(mPaytype1).isEqualTo(mPaytype2);
        mPaytype2.setId(2L);
        assertThat(mPaytype1).isNotEqualTo(mPaytype2);
        mPaytype1.setId(null);
        assertThat(mPaytype1).isNotEqualTo(mPaytype2);
    }
}

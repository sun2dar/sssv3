package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MPlywoodGrade;
import com.sssv3.repository.MPlywoodGradeRepository;
import com.sssv3.service.MPlywoodGradeService;
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
 * Test class for the MPlywoodGradeResource REST controller.
 *
 * @see MPlywoodGradeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MPlywoodGradeResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MPlywoodGradeRepository mPlywoodGradeRepository;

    @Autowired
    private MPlywoodGradeService mPlywoodGradeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMPlywoodGradeMockMvc;

    private MPlywoodGrade mPlywoodGrade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MPlywoodGradeResource mPlywoodGradeResource = new MPlywoodGradeResource(mPlywoodGradeService);
        this.restMPlywoodGradeMockMvc = MockMvcBuilders.standaloneSetup(mPlywoodGradeResource)
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
    public static MPlywoodGrade createEntity(EntityManager em) {
        MPlywoodGrade mPlywoodGrade = new MPlywoodGrade()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mPlywoodGrade;
    }

    @Before
    public void initTest() {
        mPlywoodGrade = createEntity(em);
    }

    @Test
    @Transactional
    public void createMPlywoodGrade() throws Exception {
        int databaseSizeBeforeCreate = mPlywoodGradeRepository.findAll().size();

        // Create the MPlywoodGrade
        restMPlywoodGradeMockMvc.perform(post("/api/m-plywood-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywoodGrade)))
            .andExpect(status().isCreated());

        // Validate the MPlywoodGrade in the database
        List<MPlywoodGrade> mPlywoodGradeList = mPlywoodGradeRepository.findAll();
        assertThat(mPlywoodGradeList).hasSize(databaseSizeBeforeCreate + 1);
        MPlywoodGrade testMPlywoodGrade = mPlywoodGradeList.get(mPlywoodGradeList.size() - 1);
        assertThat(testMPlywoodGrade.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMPlywoodGrade.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMPlywoodGrade.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMPlywoodGrade.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMPlywoodGradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mPlywoodGradeRepository.findAll().size();

        // Create the MPlywoodGrade with an existing ID
        mPlywoodGrade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMPlywoodGradeMockMvc.perform(post("/api/m-plywood-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywoodGrade)))
            .andExpect(status().isBadRequest());

        // Validate the MPlywoodGrade in the database
        List<MPlywoodGrade> mPlywoodGradeList = mPlywoodGradeRepository.findAll();
        assertThat(mPlywoodGradeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mPlywoodGradeRepository.findAll().size();
        // set the field null
        mPlywoodGrade.setNama(null);

        // Create the MPlywoodGrade, which fails.

        restMPlywoodGradeMockMvc.perform(post("/api/m-plywood-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywoodGrade)))
            .andExpect(status().isBadRequest());

        List<MPlywoodGrade> mPlywoodGradeList = mPlywoodGradeRepository.findAll();
        assertThat(mPlywoodGradeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMPlywoodGrades() throws Exception {
        // Initialize the database
        mPlywoodGradeRepository.saveAndFlush(mPlywoodGrade);

        // Get all the mPlywoodGradeList
        restMPlywoodGradeMockMvc.perform(get("/api/m-plywood-grades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mPlywoodGrade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMPlywoodGrade() throws Exception {
        // Initialize the database
        mPlywoodGradeRepository.saveAndFlush(mPlywoodGrade);

        // Get the mPlywoodGrade
        restMPlywoodGradeMockMvc.perform(get("/api/m-plywood-grades/{id}", mPlywoodGrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mPlywoodGrade.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMPlywoodGrade() throws Exception {
        // Get the mPlywoodGrade
        restMPlywoodGradeMockMvc.perform(get("/api/m-plywood-grades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMPlywoodGrade() throws Exception {
        // Initialize the database
        mPlywoodGradeService.save(mPlywoodGrade);

        int databaseSizeBeforeUpdate = mPlywoodGradeRepository.findAll().size();

        // Update the mPlywoodGrade
        MPlywoodGrade updatedMPlywoodGrade = mPlywoodGradeRepository.findById(mPlywoodGrade.getId()).get();
        // Disconnect from session so that the updates on updatedMPlywoodGrade are not directly saved in db
        em.detach(updatedMPlywoodGrade);
        updatedMPlywoodGrade
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMPlywoodGradeMockMvc.perform(put("/api/m-plywood-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMPlywoodGrade)))
            .andExpect(status().isOk());

        // Validate the MPlywoodGrade in the database
        List<MPlywoodGrade> mPlywoodGradeList = mPlywoodGradeRepository.findAll();
        assertThat(mPlywoodGradeList).hasSize(databaseSizeBeforeUpdate);
        MPlywoodGrade testMPlywoodGrade = mPlywoodGradeList.get(mPlywoodGradeList.size() - 1);
        assertThat(testMPlywoodGrade.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMPlywoodGrade.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMPlywoodGrade.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMPlywoodGrade.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMPlywoodGrade() throws Exception {
        int databaseSizeBeforeUpdate = mPlywoodGradeRepository.findAll().size();

        // Create the MPlywoodGrade

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMPlywoodGradeMockMvc.perform(put("/api/m-plywood-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mPlywoodGrade)))
            .andExpect(status().isBadRequest());

        // Validate the MPlywoodGrade in the database
        List<MPlywoodGrade> mPlywoodGradeList = mPlywoodGradeRepository.findAll();
        assertThat(mPlywoodGradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMPlywoodGrade() throws Exception {
        // Initialize the database
        mPlywoodGradeService.save(mPlywoodGrade);

        int databaseSizeBeforeDelete = mPlywoodGradeRepository.findAll().size();

        // Get the mPlywoodGrade
        restMPlywoodGradeMockMvc.perform(delete("/api/m-plywood-grades/{id}", mPlywoodGrade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MPlywoodGrade> mPlywoodGradeList = mPlywoodGradeRepository.findAll();
        assertThat(mPlywoodGradeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPlywoodGrade.class);
        MPlywoodGrade mPlywoodGrade1 = new MPlywoodGrade();
        mPlywoodGrade1.setId(1L);
        MPlywoodGrade mPlywoodGrade2 = new MPlywoodGrade();
        mPlywoodGrade2.setId(mPlywoodGrade1.getId());
        assertThat(mPlywoodGrade1).isEqualTo(mPlywoodGrade2);
        mPlywoodGrade2.setId(2L);
        assertThat(mPlywoodGrade1).isNotEqualTo(mPlywoodGrade2);
        mPlywoodGrade1.setId(null);
        assertThat(mPlywoodGrade1).isNotEqualTo(mPlywoodGrade2);
    }
}

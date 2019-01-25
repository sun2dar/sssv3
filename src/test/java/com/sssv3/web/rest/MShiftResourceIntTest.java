package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MShift;
import com.sssv3.repository.MShiftRepository;
import com.sssv3.service.MShiftService;
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
 * Test class for the MShiftResource REST controller.
 *
 * @see MShiftResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MShiftResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MShiftRepository mShiftRepository;

    @Autowired
    private MShiftService mShiftService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMShiftMockMvc;

    private MShift mShift;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MShiftResource mShiftResource = new MShiftResource(mShiftService);
        this.restMShiftMockMvc = MockMvcBuilders.standaloneSetup(mShiftResource)
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
    public static MShift createEntity(EntityManager em) {
        MShift mShift = new MShift()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mShift;
    }

    @Before
    public void initTest() {
        mShift = createEntity(em);
    }

    @Test
    @Transactional
    public void createMShift() throws Exception {
        int databaseSizeBeforeCreate = mShiftRepository.findAll().size();

        // Create the MShift
        restMShiftMockMvc.perform(post("/api/m-shifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mShift)))
            .andExpect(status().isCreated());

        // Validate the MShift in the database
        List<MShift> mShiftList = mShiftRepository.findAll();
        assertThat(mShiftList).hasSize(databaseSizeBeforeCreate + 1);
        MShift testMShift = mShiftList.get(mShiftList.size() - 1);
        assertThat(testMShift.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMShift.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMShift.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMShift.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMShiftWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mShiftRepository.findAll().size();

        // Create the MShift with an existing ID
        mShift.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMShiftMockMvc.perform(post("/api/m-shifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mShift)))
            .andExpect(status().isBadRequest());

        // Validate the MShift in the database
        List<MShift> mShiftList = mShiftRepository.findAll();
        assertThat(mShiftList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mShiftRepository.findAll().size();
        // set the field null
        mShift.setNama(null);

        // Create the MShift, which fails.

        restMShiftMockMvc.perform(post("/api/m-shifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mShift)))
            .andExpect(status().isBadRequest());

        List<MShift> mShiftList = mShiftRepository.findAll();
        assertThat(mShiftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMShifts() throws Exception {
        // Initialize the database
        mShiftRepository.saveAndFlush(mShift);

        // Get all the mShiftList
        restMShiftMockMvc.perform(get("/api/m-shifts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mShift.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMShift() throws Exception {
        // Initialize the database
        mShiftRepository.saveAndFlush(mShift);

        // Get the mShift
        restMShiftMockMvc.perform(get("/api/m-shifts/{id}", mShift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mShift.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMShift() throws Exception {
        // Get the mShift
        restMShiftMockMvc.perform(get("/api/m-shifts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMShift() throws Exception {
        // Initialize the database
        mShiftService.save(mShift);

        int databaseSizeBeforeUpdate = mShiftRepository.findAll().size();

        // Update the mShift
        MShift updatedMShift = mShiftRepository.findById(mShift.getId()).get();
        // Disconnect from session so that the updates on updatedMShift are not directly saved in db
        em.detach(updatedMShift);
        updatedMShift
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMShiftMockMvc.perform(put("/api/m-shifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMShift)))
            .andExpect(status().isOk());

        // Validate the MShift in the database
        List<MShift> mShiftList = mShiftRepository.findAll();
        assertThat(mShiftList).hasSize(databaseSizeBeforeUpdate);
        MShift testMShift = mShiftList.get(mShiftList.size() - 1);
        assertThat(testMShift.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMShift.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMShift.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMShift.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMShift() throws Exception {
        int databaseSizeBeforeUpdate = mShiftRepository.findAll().size();

        // Create the MShift

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMShiftMockMvc.perform(put("/api/m-shifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mShift)))
            .andExpect(status().isBadRequest());

        // Validate the MShift in the database
        List<MShift> mShiftList = mShiftRepository.findAll();
        assertThat(mShiftList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMShift() throws Exception {
        // Initialize the database
        mShiftService.save(mShift);

        int databaseSizeBeforeDelete = mShiftRepository.findAll().size();

        // Get the mShift
        restMShiftMockMvc.perform(delete("/api/m-shifts/{id}", mShift.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MShift> mShiftList = mShiftRepository.findAll();
        assertThat(mShiftList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MShift.class);
        MShift mShift1 = new MShift();
        mShift1.setId(1L);
        MShift mShift2 = new MShift();
        mShift2.setId(mShift1.getId());
        assertThat(mShift1).isEqualTo(mShift2);
        mShift2.setId(2L);
        assertThat(mShift1).isNotEqualTo(mShift2);
        mShift1.setId(null);
        assertThat(mShift1).isNotEqualTo(mShift2);
    }
}

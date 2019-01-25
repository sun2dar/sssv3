package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MLog;
import com.sssv3.repository.MLogRepository;
import com.sssv3.service.MLogService;
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
 * Test class for the MLogResource REST controller.
 *
 * @see MLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MLogResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final Double DEFAULT_DIAMETER = 1D;
    private static final Double UPDATED_DIAMETER = 2D;

    private static final Double DEFAULT_PANJANG = 1D;
    private static final Double UPDATED_PANJANG = 2D;

    private static final Double DEFAULT_HARGA_BELI = 1D;
    private static final Double UPDATED_HARGA_BELI = 2D;

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MLogRepository mLogRepository;

    @Autowired
    private MLogService mLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMLogMockMvc;

    private MLog mLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MLogResource mLogResource = new MLogResource(mLogService);
        this.restMLogMockMvc = MockMvcBuilders.standaloneSetup(mLogResource)
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
    public static MLog createEntity(EntityManager em) {
        MLog mLog = new MLog()
            .nama(DEFAULT_NAMA)
            .diameter(DEFAULT_DIAMETER)
            .panjang(DEFAULT_PANJANG)
            .hargaBeli(DEFAULT_HARGA_BELI)
            .qty(DEFAULT_QTY)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mLog;
    }

    @Before
    public void initTest() {
        mLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createMLog() throws Exception {
        int databaseSizeBeforeCreate = mLogRepository.findAll().size();

        // Create the MLog
        restMLogMockMvc.perform(post("/api/m-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLog)))
            .andExpect(status().isCreated());

        // Validate the MLog in the database
        List<MLog> mLogList = mLogRepository.findAll();
        assertThat(mLogList).hasSize(databaseSizeBeforeCreate + 1);
        MLog testMLog = mLogList.get(mLogList.size() - 1);
        assertThat(testMLog.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMLog.getDiameter()).isEqualTo(DEFAULT_DIAMETER);
        assertThat(testMLog.getPanjang()).isEqualTo(DEFAULT_PANJANG);
        assertThat(testMLog.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testMLog.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testMLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMLog.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mLogRepository.findAll().size();

        // Create the MLog with an existing ID
        mLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMLogMockMvc.perform(post("/api/m-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLog)))
            .andExpect(status().isBadRequest());

        // Validate the MLog in the database
        List<MLog> mLogList = mLogRepository.findAll();
        assertThat(mLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mLogRepository.findAll().size();
        // set the field null
        mLog.setNama(null);

        // Create the MLog, which fails.

        restMLogMockMvc.perform(post("/api/m-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLog)))
            .andExpect(status().isBadRequest());

        List<MLog> mLogList = mLogRepository.findAll();
        assertThat(mLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = mLogRepository.findAll().size();
        // set the field null
        mLog.setDiameter(null);

        // Create the MLog, which fails.

        restMLogMockMvc.perform(post("/api/m-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLog)))
            .andExpect(status().isBadRequest());

        List<MLog> mLogList = mLogRepository.findAll();
        assertThat(mLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPanjangIsRequired() throws Exception {
        int databaseSizeBeforeTest = mLogRepository.findAll().size();
        // set the field null
        mLog.setPanjang(null);

        // Create the MLog, which fails.

        restMLogMockMvc.perform(post("/api/m-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLog)))
            .andExpect(status().isBadRequest());

        List<MLog> mLogList = mLogRepository.findAll();
        assertThat(mLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMLogs() throws Exception {
        // Initialize the database
        mLogRepository.saveAndFlush(mLog);

        // Get all the mLogList
        restMLogMockMvc.perform(get("/api/m-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].diameter").value(hasItem(DEFAULT_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].panjang").value(hasItem(DEFAULT_PANJANG.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.doubleValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMLog() throws Exception {
        // Initialize the database
        mLogRepository.saveAndFlush(mLog);

        // Get the mLog
        restMLogMockMvc.perform(get("/api/m-logs/{id}", mLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mLog.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.diameter").value(DEFAULT_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.panjang").value(DEFAULT_PANJANG.doubleValue()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.doubleValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMLog() throws Exception {
        // Get the mLog
        restMLogMockMvc.perform(get("/api/m-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMLog() throws Exception {
        // Initialize the database
        mLogService.save(mLog);

        int databaseSizeBeforeUpdate = mLogRepository.findAll().size();

        // Update the mLog
        MLog updatedMLog = mLogRepository.findById(mLog.getId()).get();
        // Disconnect from session so that the updates on updatedMLog are not directly saved in db
        em.detach(updatedMLog);
        updatedMLog
            .nama(UPDATED_NAMA)
            .diameter(UPDATED_DIAMETER)
            .panjang(UPDATED_PANJANG)
            .hargaBeli(UPDATED_HARGA_BELI)
            .qty(UPDATED_QTY)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMLogMockMvc.perform(put("/api/m-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMLog)))
            .andExpect(status().isOk());

        // Validate the MLog in the database
        List<MLog> mLogList = mLogRepository.findAll();
        assertThat(mLogList).hasSize(databaseSizeBeforeUpdate);
        MLog testMLog = mLogList.get(mLogList.size() - 1);
        assertThat(testMLog.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMLog.getDiameter()).isEqualTo(UPDATED_DIAMETER);
        assertThat(testMLog.getPanjang()).isEqualTo(UPDATED_PANJANG);
        assertThat(testMLog.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testMLog.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testMLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMLog.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMLog() throws Exception {
        int databaseSizeBeforeUpdate = mLogRepository.findAll().size();

        // Create the MLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMLogMockMvc.perform(put("/api/m-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mLog)))
            .andExpect(status().isBadRequest());

        // Validate the MLog in the database
        List<MLog> mLogList = mLogRepository.findAll();
        assertThat(mLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMLog() throws Exception {
        // Initialize the database
        mLogService.save(mLog);

        int databaseSizeBeforeDelete = mLogRepository.findAll().size();

        // Get the mLog
        restMLogMockMvc.perform(delete("/api/m-logs/{id}", mLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MLog> mLogList = mLogRepository.findAll();
        assertThat(mLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MLog.class);
        MLog mLog1 = new MLog();
        mLog1.setId(1L);
        MLog mLog2 = new MLog();
        mLog2.setId(mLog1.getId());
        assertThat(mLog1).isEqualTo(mLog2);
        mLog2.setId(2L);
        assertThat(mLog1).isNotEqualTo(mLog2);
        mLog1.setId(null);
        assertThat(mLog1).isNotEqualTo(mLog2);
    }
}

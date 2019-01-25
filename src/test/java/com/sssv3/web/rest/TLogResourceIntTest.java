package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.TLog;
import com.sssv3.repository.TLogRepository;
import com.sssv3.service.TLogService;
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
import java.util.List;


import static com.sssv3.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sssv3.domain.enumeration.InOut;
/**
 * Test class for the TLogResource REST controller.
 *
 * @see TLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class TLogResourceIntTest {

    private static final Double DEFAULT_PANJANG = 1D;
    private static final Double UPDATED_PANJANG = 2D;

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;

    private static final Double DEFAULT_VOLUME = 1D;
    private static final Double UPDATED_VOLUME = 2D;

    private static final Double DEFAULT_HARGA_BELI = 1D;
    private static final Double UPDATED_HARGA_BELI = 2D;

    private static final Double DEFAULT_HARGA_TOTAL = 1D;
    private static final Double UPDATED_HARGA_TOTAL = 2D;

    private static final InOut DEFAULT_INOUT = InOut.IN;
    private static final InOut UPDATED_INOUT = InOut.OUT;

    @Autowired
    private TLogRepository tLogRepository;

    @Autowired
    private TLogService tLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTLogMockMvc;

    private TLog tLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TLogResource tLogResource = new TLogResource(tLogService);
        this.restTLogMockMvc = MockMvcBuilders.standaloneSetup(tLogResource)
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
    public static TLog createEntity(EntityManager em) {
        TLog tLog = new TLog()
            .panjang(DEFAULT_PANJANG)
            .qty(DEFAULT_QTY)
            .volume(DEFAULT_VOLUME)
            .hargaBeli(DEFAULT_HARGA_BELI)
            .hargaTotal(DEFAULT_HARGA_TOTAL)
            .inout(DEFAULT_INOUT);
        return tLog;
    }

    @Before
    public void initTest() {
        tLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createTLog() throws Exception {
        int databaseSizeBeforeCreate = tLogRepository.findAll().size();

        // Create the TLog
        restTLogMockMvc.perform(post("/api/t-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tLog)))
            .andExpect(status().isCreated());

        // Validate the TLog in the database
        List<TLog> tLogList = tLogRepository.findAll();
        assertThat(tLogList).hasSize(databaseSizeBeforeCreate + 1);
        TLog testTLog = tLogList.get(tLogList.size() - 1);
        assertThat(testTLog.getPanjang()).isEqualTo(DEFAULT_PANJANG);
        assertThat(testTLog.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testTLog.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testTLog.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testTLog.getHargaTotal()).isEqualTo(DEFAULT_HARGA_TOTAL);
        assertThat(testTLog.getInout()).isEqualTo(DEFAULT_INOUT);
    }

    @Test
    @Transactional
    public void createTLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tLogRepository.findAll().size();

        // Create the TLog with an existing ID
        tLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTLogMockMvc.perform(post("/api/t-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tLog)))
            .andExpect(status().isBadRequest());

        // Validate the TLog in the database
        List<TLog> tLogList = tLogRepository.findAll();
        assertThat(tLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTLogs() throws Exception {
        // Initialize the database
        tLogRepository.saveAndFlush(tLog);

        // Get all the tLogList
        restTLogMockMvc.perform(get("/api/t-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].panjang").value(hasItem(DEFAULT_PANJANG.doubleValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaTotal").value(hasItem(DEFAULT_HARGA_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].inout").value(hasItem(DEFAULT_INOUT.toString())));
    }
    
    @Test
    @Transactional
    public void getTLog() throws Exception {
        // Initialize the database
        tLogRepository.saveAndFlush(tLog);

        // Get the tLog
        restTLogMockMvc.perform(get("/api/t-logs/{id}", tLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tLog.getId().intValue()))
            .andExpect(jsonPath("$.panjang").value(DEFAULT_PANJANG.doubleValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.doubleValue()))
            .andExpect(jsonPath("$.hargaTotal").value(DEFAULT_HARGA_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.inout").value(DEFAULT_INOUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTLog() throws Exception {
        // Get the tLog
        restTLogMockMvc.perform(get("/api/t-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTLog() throws Exception {
        // Initialize the database
        tLogService.save(tLog);

        int databaseSizeBeforeUpdate = tLogRepository.findAll().size();

        // Update the tLog
        TLog updatedTLog = tLogRepository.findById(tLog.getId()).get();
        // Disconnect from session so that the updates on updatedTLog are not directly saved in db
        em.detach(updatedTLog);
        updatedTLog
            .panjang(UPDATED_PANJANG)
            .qty(UPDATED_QTY)
            .volume(UPDATED_VOLUME)
            .hargaBeli(UPDATED_HARGA_BELI)
            .hargaTotal(UPDATED_HARGA_TOTAL)
            .inout(UPDATED_INOUT);

        restTLogMockMvc.perform(put("/api/t-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTLog)))
            .andExpect(status().isOk());

        // Validate the TLog in the database
        List<TLog> tLogList = tLogRepository.findAll();
        assertThat(tLogList).hasSize(databaseSizeBeforeUpdate);
        TLog testTLog = tLogList.get(tLogList.size() - 1);
        assertThat(testTLog.getPanjang()).isEqualTo(UPDATED_PANJANG);
        assertThat(testTLog.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testTLog.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testTLog.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testTLog.getHargaTotal()).isEqualTo(UPDATED_HARGA_TOTAL);
        assertThat(testTLog.getInout()).isEqualTo(UPDATED_INOUT);
    }

    @Test
    @Transactional
    public void updateNonExistingTLog() throws Exception {
        int databaseSizeBeforeUpdate = tLogRepository.findAll().size();

        // Create the TLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTLogMockMvc.perform(put("/api/t-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tLog)))
            .andExpect(status().isBadRequest());

        // Validate the TLog in the database
        List<TLog> tLogList = tLogRepository.findAll();
        assertThat(tLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTLog() throws Exception {
        // Initialize the database
        tLogService.save(tLog);

        int databaseSizeBeforeDelete = tLogRepository.findAll().size();

        // Get the tLog
        restTLogMockMvc.perform(delete("/api/t-logs/{id}", tLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TLog> tLogList = tLogRepository.findAll();
        assertThat(tLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TLog.class);
        TLog tLog1 = new TLog();
        tLog1.setId(1L);
        TLog tLog2 = new TLog();
        tLog2.setId(tLog1.getId());
        assertThat(tLog1).isEqualTo(tLog2);
        tLog2.setId(2L);
        assertThat(tLog1).isNotEqualTo(tLog2);
        tLog1.setId(null);
        assertThat(tLog1).isNotEqualTo(tLog2);
    }
}

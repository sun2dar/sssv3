package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MUtang;
import com.sssv3.repository.MUtangRepository;
import com.sssv3.service.MUtangService;
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

import com.sssv3.domain.enumeration.TIPEUTANG;
import com.sssv3.domain.enumeration.Status;
/**
 * Test class for the MUtangResource REST controller.
 *
 * @see MUtangResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MUtangResourceIntTest {

    private static final Double DEFAULT_NOMINAL = 1D;
    private static final Double UPDATED_NOMINAL = 2D;

    private static final Double DEFAULT_SISA = 1D;
    private static final Double UPDATED_SISA = 2D;

    private static final LocalDate DEFAULT_DUEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final TIPEUTANG DEFAULT_TIPE = TIPEUTANG.HUTANG;
    private static final TIPEUTANG UPDATED_TIPE = TIPEUTANG.PIUTANG;

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    @Autowired
    private MUtangRepository mUtangRepository;

    @Autowired
    private MUtangService mUtangService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMUtangMockMvc;

    private MUtang mUtang;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MUtangResource mUtangResource = new MUtangResource(mUtangService);
        this.restMUtangMockMvc = MockMvcBuilders.standaloneSetup(mUtangResource)
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
    public static MUtang createEntity(EntityManager em) {
        MUtang mUtang = new MUtang()
            .nominal(DEFAULT_NOMINAL)
            .sisa(DEFAULT_SISA)
            .duedate(DEFAULT_DUEDATE)
            .tipe(DEFAULT_TIPE)
            .status(DEFAULT_STATUS);
        return mUtang;
    }

    @Before
    public void initTest() {
        mUtang = createEntity(em);
    }

    @Test
    @Transactional
    public void createMUtang() throws Exception {
        int databaseSizeBeforeCreate = mUtangRepository.findAll().size();

        // Create the MUtang
        restMUtangMockMvc.perform(post("/api/m-utangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mUtang)))
            .andExpect(status().isCreated());

        // Validate the MUtang in the database
        List<MUtang> mUtangList = mUtangRepository.findAll();
        assertThat(mUtangList).hasSize(databaseSizeBeforeCreate + 1);
        MUtang testMUtang = mUtangList.get(mUtangList.size() - 1);
        assertThat(testMUtang.getNominal()).isEqualTo(DEFAULT_NOMINAL);
        assertThat(testMUtang.getSisa()).isEqualTo(DEFAULT_SISA);
        assertThat(testMUtang.getDuedate()).isEqualTo(DEFAULT_DUEDATE);
        assertThat(testMUtang.getTipe()).isEqualTo(DEFAULT_TIPE);
        assertThat(testMUtang.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createMUtangWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mUtangRepository.findAll().size();

        // Create the MUtang with an existing ID
        mUtang.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMUtangMockMvc.perform(post("/api/m-utangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mUtang)))
            .andExpect(status().isBadRequest());

        // Validate the MUtang in the database
        List<MUtang> mUtangList = mUtangRepository.findAll();
        assertThat(mUtangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMUtangs() throws Exception {
        // Initialize the database
        mUtangRepository.saveAndFlush(mUtang);

        // Get all the mUtangList
        restMUtangMockMvc.perform(get("/api/m-utangs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mUtang.getId().intValue())))
            .andExpect(jsonPath("$.[*].nominal").value(hasItem(DEFAULT_NOMINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].sisa").value(hasItem(DEFAULT_SISA.doubleValue())))
            .andExpect(jsonPath("$.[*].duedate").value(hasItem(DEFAULT_DUEDATE.toString())))
            .andExpect(jsonPath("$.[*].tipe").value(hasItem(DEFAULT_TIPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getMUtang() throws Exception {
        // Initialize the database
        mUtangRepository.saveAndFlush(mUtang);

        // Get the mUtang
        restMUtangMockMvc.perform(get("/api/m-utangs/{id}", mUtang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mUtang.getId().intValue()))
            .andExpect(jsonPath("$.nominal").value(DEFAULT_NOMINAL.doubleValue()))
            .andExpect(jsonPath("$.sisa").value(DEFAULT_SISA.doubleValue()))
            .andExpect(jsonPath("$.duedate").value(DEFAULT_DUEDATE.toString()))
            .andExpect(jsonPath("$.tipe").value(DEFAULT_TIPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMUtang() throws Exception {
        // Get the mUtang
        restMUtangMockMvc.perform(get("/api/m-utangs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMUtang() throws Exception {
        // Initialize the database
        mUtangService.save(mUtang);

        int databaseSizeBeforeUpdate = mUtangRepository.findAll().size();

        // Update the mUtang
        MUtang updatedMUtang = mUtangRepository.findById(mUtang.getId()).get();
        // Disconnect from session so that the updates on updatedMUtang are not directly saved in db
        em.detach(updatedMUtang);
        updatedMUtang
            .nominal(UPDATED_NOMINAL)
            .sisa(UPDATED_SISA)
            .duedate(UPDATED_DUEDATE)
            .tipe(UPDATED_TIPE)
            .status(UPDATED_STATUS);

        restMUtangMockMvc.perform(put("/api/m-utangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMUtang)))
            .andExpect(status().isOk());

        // Validate the MUtang in the database
        List<MUtang> mUtangList = mUtangRepository.findAll();
        assertThat(mUtangList).hasSize(databaseSizeBeforeUpdate);
        MUtang testMUtang = mUtangList.get(mUtangList.size() - 1);
        assertThat(testMUtang.getNominal()).isEqualTo(UPDATED_NOMINAL);
        assertThat(testMUtang.getSisa()).isEqualTo(UPDATED_SISA);
        assertThat(testMUtang.getDuedate()).isEqualTo(UPDATED_DUEDATE);
        assertThat(testMUtang.getTipe()).isEqualTo(UPDATED_TIPE);
        assertThat(testMUtang.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingMUtang() throws Exception {
        int databaseSizeBeforeUpdate = mUtangRepository.findAll().size();

        // Create the MUtang

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMUtangMockMvc.perform(put("/api/m-utangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mUtang)))
            .andExpect(status().isBadRequest());

        // Validate the MUtang in the database
        List<MUtang> mUtangList = mUtangRepository.findAll();
        assertThat(mUtangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMUtang() throws Exception {
        // Initialize the database
        mUtangService.save(mUtang);

        int databaseSizeBeforeDelete = mUtangRepository.findAll().size();

        // Get the mUtang
        restMUtangMockMvc.perform(delete("/api/m-utangs/{id}", mUtang.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MUtang> mUtangList = mUtangRepository.findAll();
        assertThat(mUtangList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MUtang.class);
        MUtang mUtang1 = new MUtang();
        mUtang1.setId(1L);
        MUtang mUtang2 = new MUtang();
        mUtang2.setId(mUtang1.getId());
        assertThat(mUtang1).isEqualTo(mUtang2);
        mUtang2.setId(2L);
        assertThat(mUtang1).isNotEqualTo(mUtang2);
        mUtang1.setId(null);
        assertThat(mUtang1).isNotEqualTo(mUtang2);
    }
}

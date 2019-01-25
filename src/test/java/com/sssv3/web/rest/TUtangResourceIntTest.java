package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.TUtang;
import com.sssv3.repository.TUtangRepository;
import com.sssv3.service.TUtangService;
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

/**
 * Test class for the TUtangResource REST controller.
 *
 * @see TUtangResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class TUtangResourceIntTest {

    private static final Double DEFAULT_NOMINAL = 1D;
    private static final Double UPDATED_NOMINAL = 2D;

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TUtangRepository tUtangRepository;

    @Autowired
    private TUtangService tUtangService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTUtangMockMvc;

    private TUtang tUtang;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TUtangResource tUtangResource = new TUtangResource(tUtangService);
        this.restTUtangMockMvc = MockMvcBuilders.standaloneSetup(tUtangResource)
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
    public static TUtang createEntity(EntityManager em) {
        TUtang tUtang = new TUtang()
            .nominal(DEFAULT_NOMINAL)
            .deskripsi(DEFAULT_DESKRIPSI)
            .createdOn(DEFAULT_CREATED_ON);
        return tUtang;
    }

    @Before
    public void initTest() {
        tUtang = createEntity(em);
    }

    @Test
    @Transactional
    public void createTUtang() throws Exception {
        int databaseSizeBeforeCreate = tUtangRepository.findAll().size();

        // Create the TUtang
        restTUtangMockMvc.perform(post("/api/t-utangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tUtang)))
            .andExpect(status().isCreated());

        // Validate the TUtang in the database
        List<TUtang> tUtangList = tUtangRepository.findAll();
        assertThat(tUtangList).hasSize(databaseSizeBeforeCreate + 1);
        TUtang testTUtang = tUtangList.get(tUtangList.size() - 1);
        assertThat(testTUtang.getNominal()).isEqualTo(DEFAULT_NOMINAL);
        assertThat(testTUtang.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testTUtang.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createTUtangWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tUtangRepository.findAll().size();

        // Create the TUtang with an existing ID
        tUtang.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTUtangMockMvc.perform(post("/api/t-utangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tUtang)))
            .andExpect(status().isBadRequest());

        // Validate the TUtang in the database
        List<TUtang> tUtangList = tUtangRepository.findAll();
        assertThat(tUtangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTUtangs() throws Exception {
        // Initialize the database
        tUtangRepository.saveAndFlush(tUtang);

        // Get all the tUtangList
        restTUtangMockMvc.perform(get("/api/t-utangs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tUtang.getId().intValue())))
            .andExpect(jsonPath("$.[*].nominal").value(hasItem(DEFAULT_NOMINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getTUtang() throws Exception {
        // Initialize the database
        tUtangRepository.saveAndFlush(tUtang);

        // Get the tUtang
        restTUtangMockMvc.perform(get("/api/t-utangs/{id}", tUtang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tUtang.getId().intValue()))
            .andExpect(jsonPath("$.nominal").value(DEFAULT_NOMINAL.doubleValue()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTUtang() throws Exception {
        // Get the tUtang
        restTUtangMockMvc.perform(get("/api/t-utangs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTUtang() throws Exception {
        // Initialize the database
        tUtangService.save(tUtang);

        int databaseSizeBeforeUpdate = tUtangRepository.findAll().size();

        // Update the tUtang
        TUtang updatedTUtang = tUtangRepository.findById(tUtang.getId()).get();
        // Disconnect from session so that the updates on updatedTUtang are not directly saved in db
        em.detach(updatedTUtang);
        updatedTUtang
            .nominal(UPDATED_NOMINAL)
            .deskripsi(UPDATED_DESKRIPSI)
            .createdOn(UPDATED_CREATED_ON);

        restTUtangMockMvc.perform(put("/api/t-utangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTUtang)))
            .andExpect(status().isOk());

        // Validate the TUtang in the database
        List<TUtang> tUtangList = tUtangRepository.findAll();
        assertThat(tUtangList).hasSize(databaseSizeBeforeUpdate);
        TUtang testTUtang = tUtangList.get(tUtangList.size() - 1);
        assertThat(testTUtang.getNominal()).isEqualTo(UPDATED_NOMINAL);
        assertThat(testTUtang.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testTUtang.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingTUtang() throws Exception {
        int databaseSizeBeforeUpdate = tUtangRepository.findAll().size();

        // Create the TUtang

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTUtangMockMvc.perform(put("/api/t-utangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tUtang)))
            .andExpect(status().isBadRequest());

        // Validate the TUtang in the database
        List<TUtang> tUtangList = tUtangRepository.findAll();
        assertThat(tUtangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTUtang() throws Exception {
        // Initialize the database
        tUtangService.save(tUtang);

        int databaseSizeBeforeDelete = tUtangRepository.findAll().size();

        // Get the tUtang
        restTUtangMockMvc.perform(delete("/api/t-utangs/{id}", tUtang.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TUtang> tUtangList = tUtangRepository.findAll();
        assertThat(tUtangList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TUtang.class);
        TUtang tUtang1 = new TUtang();
        tUtang1.setId(1L);
        TUtang tUtang2 = new TUtang();
        tUtang2.setId(tUtang1.getId());
        assertThat(tUtang1).isEqualTo(tUtang2);
        tUtang2.setId(2L);
        assertThat(tUtang1).isNotEqualTo(tUtang2);
        tUtang1.setId(null);
        assertThat(tUtang1).isNotEqualTo(tUtang2);
    }
}

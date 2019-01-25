package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.TOperasional;
import com.sssv3.repository.TOperasionalRepository;
import com.sssv3.service.TOperasionalService;
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

/**
 * Test class for the TOperasionalResource REST controller.
 *
 * @see TOperasionalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class TOperasionalResourceIntTest {

    private static final LocalDate DEFAULT_TANGGAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TANGGAL = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_NOMINAL = 1D;
    private static final Double UPDATED_NOMINAL = 2D;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TOperasionalRepository tOperasionalRepository;

    @Autowired
    private TOperasionalService tOperasionalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTOperasionalMockMvc;

    private TOperasional tOperasional;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TOperasionalResource tOperasionalResource = new TOperasionalResource(tOperasionalService);
        this.restTOperasionalMockMvc = MockMvcBuilders.standaloneSetup(tOperasionalResource)
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
    public static TOperasional createEntity(EntityManager em) {
        TOperasional tOperasional = new TOperasional()
            .tanggal(DEFAULT_TANGGAL)
            .nominal(DEFAULT_NOMINAL)
            .createdOn(DEFAULT_CREATED_ON);
        return tOperasional;
    }

    @Before
    public void initTest() {
        tOperasional = createEntity(em);
    }

    @Test
    @Transactional
    public void createTOperasional() throws Exception {
        int databaseSizeBeforeCreate = tOperasionalRepository.findAll().size();

        // Create the TOperasional
        restTOperasionalMockMvc.perform(post("/api/t-operasionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tOperasional)))
            .andExpect(status().isCreated());

        // Validate the TOperasional in the database
        List<TOperasional> tOperasionalList = tOperasionalRepository.findAll();
        assertThat(tOperasionalList).hasSize(databaseSizeBeforeCreate + 1);
        TOperasional testTOperasional = tOperasionalList.get(tOperasionalList.size() - 1);
        assertThat(testTOperasional.getTanggal()).isEqualTo(DEFAULT_TANGGAL);
        assertThat(testTOperasional.getNominal()).isEqualTo(DEFAULT_NOMINAL);
        assertThat(testTOperasional.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createTOperasionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tOperasionalRepository.findAll().size();

        // Create the TOperasional with an existing ID
        tOperasional.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTOperasionalMockMvc.perform(post("/api/t-operasionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tOperasional)))
            .andExpect(status().isBadRequest());

        // Validate the TOperasional in the database
        List<TOperasional> tOperasionalList = tOperasionalRepository.findAll();
        assertThat(tOperasionalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTOperasionals() throws Exception {
        // Initialize the database
        tOperasionalRepository.saveAndFlush(tOperasional);

        // Get all the tOperasionalList
        restTOperasionalMockMvc.perform(get("/api/t-operasionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tOperasional.getId().intValue())))
            .andExpect(jsonPath("$.[*].tanggal").value(hasItem(DEFAULT_TANGGAL.toString())))
            .andExpect(jsonPath("$.[*].nominal").value(hasItem(DEFAULT_NOMINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getTOperasional() throws Exception {
        // Initialize the database
        tOperasionalRepository.saveAndFlush(tOperasional);

        // Get the tOperasional
        restTOperasionalMockMvc.perform(get("/api/t-operasionals/{id}", tOperasional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tOperasional.getId().intValue()))
            .andExpect(jsonPath("$.tanggal").value(DEFAULT_TANGGAL.toString()))
            .andExpect(jsonPath("$.nominal").value(DEFAULT_NOMINAL.doubleValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTOperasional() throws Exception {
        // Get the tOperasional
        restTOperasionalMockMvc.perform(get("/api/t-operasionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTOperasional() throws Exception {
        // Initialize the database
        tOperasionalService.save(tOperasional);

        int databaseSizeBeforeUpdate = tOperasionalRepository.findAll().size();

        // Update the tOperasional
        TOperasional updatedTOperasional = tOperasionalRepository.findById(tOperasional.getId()).get();
        // Disconnect from session so that the updates on updatedTOperasional are not directly saved in db
        em.detach(updatedTOperasional);
        updatedTOperasional
            .tanggal(UPDATED_TANGGAL)
            .nominal(UPDATED_NOMINAL)
            .createdOn(UPDATED_CREATED_ON);

        restTOperasionalMockMvc.perform(put("/api/t-operasionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTOperasional)))
            .andExpect(status().isOk());

        // Validate the TOperasional in the database
        List<TOperasional> tOperasionalList = tOperasionalRepository.findAll();
        assertThat(tOperasionalList).hasSize(databaseSizeBeforeUpdate);
        TOperasional testTOperasional = tOperasionalList.get(tOperasionalList.size() - 1);
        assertThat(testTOperasional.getTanggal()).isEqualTo(UPDATED_TANGGAL);
        assertThat(testTOperasional.getNominal()).isEqualTo(UPDATED_NOMINAL);
        assertThat(testTOperasional.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingTOperasional() throws Exception {
        int databaseSizeBeforeUpdate = tOperasionalRepository.findAll().size();

        // Create the TOperasional

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTOperasionalMockMvc.perform(put("/api/t-operasionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tOperasional)))
            .andExpect(status().isBadRequest());

        // Validate the TOperasional in the database
        List<TOperasional> tOperasionalList = tOperasionalRepository.findAll();
        assertThat(tOperasionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTOperasional() throws Exception {
        // Initialize the database
        tOperasionalService.save(tOperasional);

        int databaseSizeBeforeDelete = tOperasionalRepository.findAll().size();

        // Get the tOperasional
        restTOperasionalMockMvc.perform(delete("/api/t-operasionals/{id}", tOperasional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TOperasional> tOperasionalList = tOperasionalRepository.findAll();
        assertThat(tOperasionalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TOperasional.class);
        TOperasional tOperasional1 = new TOperasional();
        tOperasional1.setId(1L);
        TOperasional tOperasional2 = new TOperasional();
        tOperasional2.setId(tOperasional1.getId());
        assertThat(tOperasional1).isEqualTo(tOperasional2);
        tOperasional2.setId(2L);
        assertThat(tOperasional1).isNotEqualTo(tOperasional2);
        tOperasional1.setId(null);
        assertThat(tOperasional1).isNotEqualTo(tOperasional2);
    }
}

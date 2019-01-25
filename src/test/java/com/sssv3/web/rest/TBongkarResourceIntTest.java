package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.TBongkar;
import com.sssv3.repository.TBongkarRepository;
import com.sssv3.service.TBongkarService;
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

/**
 * Test class for the TBongkarResource REST controller.
 *
 * @see TBongkarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class TBongkarResourceIntTest {

    private static final Double DEFAULT_RATE_UPAH = 1D;
    private static final Double UPDATED_RATE_UPAH = 2D;

    private static final Double DEFAULT_VOLUME = 1D;
    private static final Double UPDATED_VOLUME = 2D;

    @Autowired
    private TBongkarRepository tBongkarRepository;

    @Autowired
    private TBongkarService tBongkarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTBongkarMockMvc;

    private TBongkar tBongkar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TBongkarResource tBongkarResource = new TBongkarResource(tBongkarService);
        this.restTBongkarMockMvc = MockMvcBuilders.standaloneSetup(tBongkarResource)
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
    public static TBongkar createEntity(EntityManager em) {
        TBongkar tBongkar = new TBongkar()
            .rateUpah(DEFAULT_RATE_UPAH)
            .volume(DEFAULT_VOLUME);
        return tBongkar;
    }

    @Before
    public void initTest() {
        tBongkar = createEntity(em);
    }

    @Test
    @Transactional
    public void createTBongkar() throws Exception {
        int databaseSizeBeforeCreate = tBongkarRepository.findAll().size();

        // Create the TBongkar
        restTBongkarMockMvc.perform(post("/api/t-bongkars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBongkar)))
            .andExpect(status().isCreated());

        // Validate the TBongkar in the database
        List<TBongkar> tBongkarList = tBongkarRepository.findAll();
        assertThat(tBongkarList).hasSize(databaseSizeBeforeCreate + 1);
        TBongkar testTBongkar = tBongkarList.get(tBongkarList.size() - 1);
        assertThat(testTBongkar.getRateUpah()).isEqualTo(DEFAULT_RATE_UPAH);
        assertThat(testTBongkar.getVolume()).isEqualTo(DEFAULT_VOLUME);
    }

    @Test
    @Transactional
    public void createTBongkarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tBongkarRepository.findAll().size();

        // Create the TBongkar with an existing ID
        tBongkar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBongkarMockMvc.perform(post("/api/t-bongkars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBongkar)))
            .andExpect(status().isBadRequest());

        // Validate the TBongkar in the database
        List<TBongkar> tBongkarList = tBongkarRepository.findAll();
        assertThat(tBongkarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTBongkars() throws Exception {
        // Initialize the database
        tBongkarRepository.saveAndFlush(tBongkar);

        // Get all the tBongkarList
        restTBongkarMockMvc.perform(get("/api/t-bongkars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBongkar.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateUpah").value(hasItem(DEFAULT_RATE_UPAH.doubleValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTBongkar() throws Exception {
        // Initialize the database
        tBongkarRepository.saveAndFlush(tBongkar);

        // Get the tBongkar
        restTBongkarMockMvc.perform(get("/api/t-bongkars/{id}", tBongkar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tBongkar.getId().intValue()))
            .andExpect(jsonPath("$.rateUpah").value(DEFAULT_RATE_UPAH.doubleValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTBongkar() throws Exception {
        // Get the tBongkar
        restTBongkarMockMvc.perform(get("/api/t-bongkars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTBongkar() throws Exception {
        // Initialize the database
        tBongkarService.save(tBongkar);

        int databaseSizeBeforeUpdate = tBongkarRepository.findAll().size();

        // Update the tBongkar
        TBongkar updatedTBongkar = tBongkarRepository.findById(tBongkar.getId()).get();
        // Disconnect from session so that the updates on updatedTBongkar are not directly saved in db
        em.detach(updatedTBongkar);
        updatedTBongkar
            .rateUpah(UPDATED_RATE_UPAH)
            .volume(UPDATED_VOLUME);

        restTBongkarMockMvc.perform(put("/api/t-bongkars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTBongkar)))
            .andExpect(status().isOk());

        // Validate the TBongkar in the database
        List<TBongkar> tBongkarList = tBongkarRepository.findAll();
        assertThat(tBongkarList).hasSize(databaseSizeBeforeUpdate);
        TBongkar testTBongkar = tBongkarList.get(tBongkarList.size() - 1);
        assertThat(testTBongkar.getRateUpah()).isEqualTo(UPDATED_RATE_UPAH);
        assertThat(testTBongkar.getVolume()).isEqualTo(UPDATED_VOLUME);
    }

    @Test
    @Transactional
    public void updateNonExistingTBongkar() throws Exception {
        int databaseSizeBeforeUpdate = tBongkarRepository.findAll().size();

        // Create the TBongkar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTBongkarMockMvc.perform(put("/api/t-bongkars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBongkar)))
            .andExpect(status().isBadRequest());

        // Validate the TBongkar in the database
        List<TBongkar> tBongkarList = tBongkarRepository.findAll();
        assertThat(tBongkarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTBongkar() throws Exception {
        // Initialize the database
        tBongkarService.save(tBongkar);

        int databaseSizeBeforeDelete = tBongkarRepository.findAll().size();

        // Get the tBongkar
        restTBongkarMockMvc.perform(delete("/api/t-bongkars/{id}", tBongkar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TBongkar> tBongkarList = tBongkarRepository.findAll();
        assertThat(tBongkarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBongkar.class);
        TBongkar tBongkar1 = new TBongkar();
        tBongkar1.setId(1L);
        TBongkar tBongkar2 = new TBongkar();
        tBongkar2.setId(tBongkar1.getId());
        assertThat(tBongkar1).isEqualTo(tBongkar2);
        tBongkar2.setId(2L);
        assertThat(tBongkar1).isNotEqualTo(tBongkar2);
        tBongkar1.setId(null);
        assertThat(tBongkar1).isNotEqualTo(tBongkar2);
    }
}

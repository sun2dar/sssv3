package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.TPlywood;
import com.sssv3.repository.TPlywoodRepository;
import com.sssv3.service.TPlywoodService;
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
 * Test class for the TPlywoodResource REST controller.
 *
 * @see TPlywoodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class TPlywoodResourceIntTest {

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
    private TPlywoodRepository tPlywoodRepository;

    @Autowired
    private TPlywoodService tPlywoodService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTPlywoodMockMvc;

    private TPlywood tPlywood;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TPlywoodResource tPlywoodResource = new TPlywoodResource(tPlywoodService);
        this.restTPlywoodMockMvc = MockMvcBuilders.standaloneSetup(tPlywoodResource)
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
    public static TPlywood createEntity(EntityManager em) {
        TPlywood tPlywood = new TPlywood()
            .qty(DEFAULT_QTY)
            .volume(DEFAULT_VOLUME)
            .hargaBeli(DEFAULT_HARGA_BELI)
            .hargaTotal(DEFAULT_HARGA_TOTAL)
            .inout(DEFAULT_INOUT);
        return tPlywood;
    }

    @Before
    public void initTest() {
        tPlywood = createEntity(em);
    }

    @Test
    @Transactional
    public void createTPlywood() throws Exception {
        int databaseSizeBeforeCreate = tPlywoodRepository.findAll().size();

        // Create the TPlywood
        restTPlywoodMockMvc.perform(post("/api/t-plywoods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tPlywood)))
            .andExpect(status().isCreated());

        // Validate the TPlywood in the database
        List<TPlywood> tPlywoodList = tPlywoodRepository.findAll();
        assertThat(tPlywoodList).hasSize(databaseSizeBeforeCreate + 1);
        TPlywood testTPlywood = tPlywoodList.get(tPlywoodList.size() - 1);
        assertThat(testTPlywood.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testTPlywood.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testTPlywood.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testTPlywood.getHargaTotal()).isEqualTo(DEFAULT_HARGA_TOTAL);
        assertThat(testTPlywood.getInout()).isEqualTo(DEFAULT_INOUT);
    }

    @Test
    @Transactional
    public void createTPlywoodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tPlywoodRepository.findAll().size();

        // Create the TPlywood with an existing ID
        tPlywood.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTPlywoodMockMvc.perform(post("/api/t-plywoods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tPlywood)))
            .andExpect(status().isBadRequest());

        // Validate the TPlywood in the database
        List<TPlywood> tPlywoodList = tPlywoodRepository.findAll();
        assertThat(tPlywoodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTPlywoods() throws Exception {
        // Initialize the database
        tPlywoodRepository.saveAndFlush(tPlywood);

        // Get all the tPlywoodList
        restTPlywoodMockMvc.perform(get("/api/t-plywoods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tPlywood.getId().intValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaTotal").value(hasItem(DEFAULT_HARGA_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].inout").value(hasItem(DEFAULT_INOUT.toString())));
    }
    
    @Test
    @Transactional
    public void getTPlywood() throws Exception {
        // Initialize the database
        tPlywoodRepository.saveAndFlush(tPlywood);

        // Get the tPlywood
        restTPlywoodMockMvc.perform(get("/api/t-plywoods/{id}", tPlywood.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tPlywood.getId().intValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.doubleValue()))
            .andExpect(jsonPath("$.hargaTotal").value(DEFAULT_HARGA_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.inout").value(DEFAULT_INOUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTPlywood() throws Exception {
        // Get the tPlywood
        restTPlywoodMockMvc.perform(get("/api/t-plywoods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTPlywood() throws Exception {
        // Initialize the database
        tPlywoodService.save(tPlywood);

        int databaseSizeBeforeUpdate = tPlywoodRepository.findAll().size();

        // Update the tPlywood
        TPlywood updatedTPlywood = tPlywoodRepository.findById(tPlywood.getId()).get();
        // Disconnect from session so that the updates on updatedTPlywood are not directly saved in db
        em.detach(updatedTPlywood);
        updatedTPlywood
            .qty(UPDATED_QTY)
            .volume(UPDATED_VOLUME)
            .hargaBeli(UPDATED_HARGA_BELI)
            .hargaTotal(UPDATED_HARGA_TOTAL)
            .inout(UPDATED_INOUT);

        restTPlywoodMockMvc.perform(put("/api/t-plywoods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTPlywood)))
            .andExpect(status().isOk());

        // Validate the TPlywood in the database
        List<TPlywood> tPlywoodList = tPlywoodRepository.findAll();
        assertThat(tPlywoodList).hasSize(databaseSizeBeforeUpdate);
        TPlywood testTPlywood = tPlywoodList.get(tPlywoodList.size() - 1);
        assertThat(testTPlywood.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testTPlywood.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testTPlywood.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testTPlywood.getHargaTotal()).isEqualTo(UPDATED_HARGA_TOTAL);
        assertThat(testTPlywood.getInout()).isEqualTo(UPDATED_INOUT);
    }

    @Test
    @Transactional
    public void updateNonExistingTPlywood() throws Exception {
        int databaseSizeBeforeUpdate = tPlywoodRepository.findAll().size();

        // Create the TPlywood

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTPlywoodMockMvc.perform(put("/api/t-plywoods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tPlywood)))
            .andExpect(status().isBadRequest());

        // Validate the TPlywood in the database
        List<TPlywood> tPlywoodList = tPlywoodRepository.findAll();
        assertThat(tPlywoodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTPlywood() throws Exception {
        // Initialize the database
        tPlywoodService.save(tPlywood);

        int databaseSizeBeforeDelete = tPlywoodRepository.findAll().size();

        // Get the tPlywood
        restTPlywoodMockMvc.perform(delete("/api/t-plywoods/{id}", tPlywood.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TPlywood> tPlywoodList = tPlywoodRepository.findAll();
        assertThat(tPlywoodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TPlywood.class);
        TPlywood tPlywood1 = new TPlywood();
        tPlywood1.setId(1L);
        TPlywood tPlywood2 = new TPlywood();
        tPlywood2.setId(tPlywood1.getId());
        assertThat(tPlywood1).isEqualTo(tPlywood2);
        tPlywood2.setId(2L);
        assertThat(tPlywood1).isNotEqualTo(tPlywood2);
        tPlywood1.setId(null);
        assertThat(tPlywood1).isNotEqualTo(tPlywood2);
    }
}

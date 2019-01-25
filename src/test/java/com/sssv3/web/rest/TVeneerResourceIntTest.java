package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.TVeneer;
import com.sssv3.repository.TVeneerRepository;
import com.sssv3.service.TVeneerService;
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
 * Test class for the TVeneerResource REST controller.
 *
 * @see TVeneerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class TVeneerResourceIntTest {

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
    private TVeneerRepository tVeneerRepository;

    @Autowired
    private TVeneerService tVeneerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTVeneerMockMvc;

    private TVeneer tVeneer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TVeneerResource tVeneerResource = new TVeneerResource(tVeneerService);
        this.restTVeneerMockMvc = MockMvcBuilders.standaloneSetup(tVeneerResource)
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
    public static TVeneer createEntity(EntityManager em) {
        TVeneer tVeneer = new TVeneer()
            .qty(DEFAULT_QTY)
            .volume(DEFAULT_VOLUME)
            .hargaBeli(DEFAULT_HARGA_BELI)
            .hargaTotal(DEFAULT_HARGA_TOTAL)
            .inout(DEFAULT_INOUT);
        return tVeneer;
    }

    @Before
    public void initTest() {
        tVeneer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTVeneer() throws Exception {
        int databaseSizeBeforeCreate = tVeneerRepository.findAll().size();

        // Create the TVeneer
        restTVeneerMockMvc.perform(post("/api/t-veneers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tVeneer)))
            .andExpect(status().isCreated());

        // Validate the TVeneer in the database
        List<TVeneer> tVeneerList = tVeneerRepository.findAll();
        assertThat(tVeneerList).hasSize(databaseSizeBeforeCreate + 1);
        TVeneer testTVeneer = tVeneerList.get(tVeneerList.size() - 1);
        assertThat(testTVeneer.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testTVeneer.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testTVeneer.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testTVeneer.getHargaTotal()).isEqualTo(DEFAULT_HARGA_TOTAL);
        assertThat(testTVeneer.getInout()).isEqualTo(DEFAULT_INOUT);
    }

    @Test
    @Transactional
    public void createTVeneerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tVeneerRepository.findAll().size();

        // Create the TVeneer with an existing ID
        tVeneer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTVeneerMockMvc.perform(post("/api/t-veneers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tVeneer)))
            .andExpect(status().isBadRequest());

        // Validate the TVeneer in the database
        List<TVeneer> tVeneerList = tVeneerRepository.findAll();
        assertThat(tVeneerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTVeneers() throws Exception {
        // Initialize the database
        tVeneerRepository.saveAndFlush(tVeneer);

        // Get all the tVeneerList
        restTVeneerMockMvc.perform(get("/api/t-veneers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tVeneer.getId().intValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaTotal").value(hasItem(DEFAULT_HARGA_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].inout").value(hasItem(DEFAULT_INOUT.toString())));
    }
    
    @Test
    @Transactional
    public void getTVeneer() throws Exception {
        // Initialize the database
        tVeneerRepository.saveAndFlush(tVeneer);

        // Get the tVeneer
        restTVeneerMockMvc.perform(get("/api/t-veneers/{id}", tVeneer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tVeneer.getId().intValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.doubleValue()))
            .andExpect(jsonPath("$.hargaTotal").value(DEFAULT_HARGA_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.inout").value(DEFAULT_INOUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTVeneer() throws Exception {
        // Get the tVeneer
        restTVeneerMockMvc.perform(get("/api/t-veneers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTVeneer() throws Exception {
        // Initialize the database
        tVeneerService.save(tVeneer);

        int databaseSizeBeforeUpdate = tVeneerRepository.findAll().size();

        // Update the tVeneer
        TVeneer updatedTVeneer = tVeneerRepository.findById(tVeneer.getId()).get();
        // Disconnect from session so that the updates on updatedTVeneer are not directly saved in db
        em.detach(updatedTVeneer);
        updatedTVeneer
            .qty(UPDATED_QTY)
            .volume(UPDATED_VOLUME)
            .hargaBeli(UPDATED_HARGA_BELI)
            .hargaTotal(UPDATED_HARGA_TOTAL)
            .inout(UPDATED_INOUT);

        restTVeneerMockMvc.perform(put("/api/t-veneers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTVeneer)))
            .andExpect(status().isOk());

        // Validate the TVeneer in the database
        List<TVeneer> tVeneerList = tVeneerRepository.findAll();
        assertThat(tVeneerList).hasSize(databaseSizeBeforeUpdate);
        TVeneer testTVeneer = tVeneerList.get(tVeneerList.size() - 1);
        assertThat(testTVeneer.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testTVeneer.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testTVeneer.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testTVeneer.getHargaTotal()).isEqualTo(UPDATED_HARGA_TOTAL);
        assertThat(testTVeneer.getInout()).isEqualTo(UPDATED_INOUT);
    }

    @Test
    @Transactional
    public void updateNonExistingTVeneer() throws Exception {
        int databaseSizeBeforeUpdate = tVeneerRepository.findAll().size();

        // Create the TVeneer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTVeneerMockMvc.perform(put("/api/t-veneers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tVeneer)))
            .andExpect(status().isBadRequest());

        // Validate the TVeneer in the database
        List<TVeneer> tVeneerList = tVeneerRepository.findAll();
        assertThat(tVeneerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTVeneer() throws Exception {
        // Initialize the database
        tVeneerService.save(tVeneer);

        int databaseSizeBeforeDelete = tVeneerRepository.findAll().size();

        // Get the tVeneer
        restTVeneerMockMvc.perform(delete("/api/t-veneers/{id}", tVeneer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TVeneer> tVeneerList = tVeneerRepository.findAll();
        assertThat(tVeneerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TVeneer.class);
        TVeneer tVeneer1 = new TVeneer();
        tVeneer1.setId(1L);
        TVeneer tVeneer2 = new TVeneer();
        tVeneer2.setId(tVeneer1.getId());
        assertThat(tVeneer1).isEqualTo(tVeneer2);
        tVeneer2.setId(2L);
        assertThat(tVeneer1).isNotEqualTo(tVeneer2);
        tVeneer1.setId(null);
        assertThat(tVeneer1).isNotEqualTo(tVeneer2);
    }
}

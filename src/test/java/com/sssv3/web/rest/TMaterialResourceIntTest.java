package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.TMaterial;
import com.sssv3.repository.TMaterialRepository;
import com.sssv3.service.TMaterialService;
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
 * Test class for the TMaterialResource REST controller.
 *
 * @see TMaterialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class TMaterialResourceIntTest {

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;

    private static final Double DEFAULT_HARGA_BELI = 1D;
    private static final Double UPDATED_HARGA_BELI = 2D;

    private static final Double DEFAULT_HARGA_TOTAL = 1D;
    private static final Double UPDATED_HARGA_TOTAL = 2D;

    private static final InOut DEFAULT_INOUT = InOut.IN;
    private static final InOut UPDATED_INOUT = InOut.OUT;

    @Autowired
    private TMaterialRepository tMaterialRepository;

    @Autowired
    private TMaterialService tMaterialService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTMaterialMockMvc;

    private TMaterial tMaterial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TMaterialResource tMaterialResource = new TMaterialResource(tMaterialService);
        this.restTMaterialMockMvc = MockMvcBuilders.standaloneSetup(tMaterialResource)
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
    public static TMaterial createEntity(EntityManager em) {
        TMaterial tMaterial = new TMaterial()
            .qty(DEFAULT_QTY)
            .hargaBeli(DEFAULT_HARGA_BELI)
            .hargaTotal(DEFAULT_HARGA_TOTAL)
            .inout(DEFAULT_INOUT);
        return tMaterial;
    }

    @Before
    public void initTest() {
        tMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createTMaterial() throws Exception {
        int databaseSizeBeforeCreate = tMaterialRepository.findAll().size();

        // Create the TMaterial
        restTMaterialMockMvc.perform(post("/api/t-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tMaterial)))
            .andExpect(status().isCreated());

        // Validate the TMaterial in the database
        List<TMaterial> tMaterialList = tMaterialRepository.findAll();
        assertThat(tMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        TMaterial testTMaterial = tMaterialList.get(tMaterialList.size() - 1);
        assertThat(testTMaterial.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testTMaterial.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testTMaterial.getHargaTotal()).isEqualTo(DEFAULT_HARGA_TOTAL);
        assertThat(testTMaterial.getInout()).isEqualTo(DEFAULT_INOUT);
    }

    @Test
    @Transactional
    public void createTMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tMaterialRepository.findAll().size();

        // Create the TMaterial with an existing ID
        tMaterial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTMaterialMockMvc.perform(post("/api/t-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the TMaterial in the database
        List<TMaterial> tMaterialList = tMaterialRepository.findAll();
        assertThat(tMaterialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTMaterials() throws Exception {
        // Initialize the database
        tMaterialRepository.saveAndFlush(tMaterial);

        // Get all the tMaterialList
        restTMaterialMockMvc.perform(get("/api/t-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.doubleValue())))
            .andExpect(jsonPath("$.[*].hargaTotal").value(hasItem(DEFAULT_HARGA_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].inout").value(hasItem(DEFAULT_INOUT.toString())));
    }
    
    @Test
    @Transactional
    public void getTMaterial() throws Exception {
        // Initialize the database
        tMaterialRepository.saveAndFlush(tMaterial);

        // Get the tMaterial
        restTMaterialMockMvc.perform(get("/api/t-materials/{id}", tMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tMaterial.getId().intValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.doubleValue()))
            .andExpect(jsonPath("$.hargaTotal").value(DEFAULT_HARGA_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.inout").value(DEFAULT_INOUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTMaterial() throws Exception {
        // Get the tMaterial
        restTMaterialMockMvc.perform(get("/api/t-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTMaterial() throws Exception {
        // Initialize the database
        tMaterialService.save(tMaterial);

        int databaseSizeBeforeUpdate = tMaterialRepository.findAll().size();

        // Update the tMaterial
        TMaterial updatedTMaterial = tMaterialRepository.findById(tMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedTMaterial are not directly saved in db
        em.detach(updatedTMaterial);
        updatedTMaterial
            .qty(UPDATED_QTY)
            .hargaBeli(UPDATED_HARGA_BELI)
            .hargaTotal(UPDATED_HARGA_TOTAL)
            .inout(UPDATED_INOUT);

        restTMaterialMockMvc.perform(put("/api/t-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTMaterial)))
            .andExpect(status().isOk());

        // Validate the TMaterial in the database
        List<TMaterial> tMaterialList = tMaterialRepository.findAll();
        assertThat(tMaterialList).hasSize(databaseSizeBeforeUpdate);
        TMaterial testTMaterial = tMaterialList.get(tMaterialList.size() - 1);
        assertThat(testTMaterial.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testTMaterial.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testTMaterial.getHargaTotal()).isEqualTo(UPDATED_HARGA_TOTAL);
        assertThat(testTMaterial.getInout()).isEqualTo(UPDATED_INOUT);
    }

    @Test
    @Transactional
    public void updateNonExistingTMaterial() throws Exception {
        int databaseSizeBeforeUpdate = tMaterialRepository.findAll().size();

        // Create the TMaterial

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTMaterialMockMvc.perform(put("/api/t-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the TMaterial in the database
        List<TMaterial> tMaterialList = tMaterialRepository.findAll();
        assertThat(tMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTMaterial() throws Exception {
        // Initialize the database
        tMaterialService.save(tMaterial);

        int databaseSizeBeforeDelete = tMaterialRepository.findAll().size();

        // Get the tMaterial
        restTMaterialMockMvc.perform(delete("/api/t-materials/{id}", tMaterial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TMaterial> tMaterialList = tMaterialRepository.findAll();
        assertThat(tMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TMaterial.class);
        TMaterial tMaterial1 = new TMaterial();
        tMaterial1.setId(1L);
        TMaterial tMaterial2 = new TMaterial();
        tMaterial2.setId(tMaterial1.getId());
        assertThat(tMaterial1).isEqualTo(tMaterial2);
        tMaterial2.setId(2L);
        assertThat(tMaterial1).isNotEqualTo(tMaterial2);
        tMaterial1.setId(null);
        assertThat(tMaterial1).isNotEqualTo(tMaterial2);
    }
}

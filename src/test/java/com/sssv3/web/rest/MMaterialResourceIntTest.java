package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MMaterial;
import com.sssv3.repository.MMaterialRepository;
import com.sssv3.service.MMaterialService;
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
 * Test class for the MMaterialResource REST controller.
 *
 * @see MMaterialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MMaterialResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Double DEFAULT_HARGA = 1D;
    private static final Double UPDATED_HARGA = 2D;

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MMaterialRepository mMaterialRepository;

    @Autowired
    private MMaterialService mMaterialService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMMaterialMockMvc;

    private MMaterial mMaterial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MMaterialResource mMaterialResource = new MMaterialResource(mMaterialService);
        this.restMMaterialMockMvc = MockMvcBuilders.standaloneSetup(mMaterialResource)
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
    public static MMaterial createEntity(EntityManager em) {
        MMaterial mMaterial = new MMaterial()
            .nama(DEFAULT_NAMA)
            .deskripsi(DEFAULT_DESKRIPSI)
            .harga(DEFAULT_HARGA)
            .qty(DEFAULT_QTY)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mMaterial;
    }

    @Before
    public void initTest() {
        mMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createMMaterial() throws Exception {
        int databaseSizeBeforeCreate = mMaterialRepository.findAll().size();

        // Create the MMaterial
        restMMaterialMockMvc.perform(post("/api/m-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMaterial)))
            .andExpect(status().isCreated());

        // Validate the MMaterial in the database
        List<MMaterial> mMaterialList = mMaterialRepository.findAll();
        assertThat(mMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        MMaterial testMMaterial = mMaterialList.get(mMaterialList.size() - 1);
        assertThat(testMMaterial.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMMaterial.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMMaterial.getHarga()).isEqualTo(DEFAULT_HARGA);
        assertThat(testMMaterial.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testMMaterial.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMMaterial.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mMaterialRepository.findAll().size();

        // Create the MMaterial with an existing ID
        mMaterial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMMaterialMockMvc.perform(post("/api/m-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the MMaterial in the database
        List<MMaterial> mMaterialList = mMaterialRepository.findAll();
        assertThat(mMaterialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mMaterialRepository.findAll().size();
        // set the field null
        mMaterial.setNama(null);

        // Create the MMaterial, which fails.

        restMMaterialMockMvc.perform(post("/api/m-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMaterial)))
            .andExpect(status().isBadRequest());

        List<MMaterial> mMaterialList = mMaterialRepository.findAll();
        assertThat(mMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHargaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mMaterialRepository.findAll().size();
        // set the field null
        mMaterial.setHarga(null);

        // Create the MMaterial, which fails.

        restMMaterialMockMvc.perform(post("/api/m-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMaterial)))
            .andExpect(status().isBadRequest());

        List<MMaterial> mMaterialList = mMaterialRepository.findAll();
        assertThat(mMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = mMaterialRepository.findAll().size();
        // set the field null
        mMaterial.setQty(null);

        // Create the MMaterial, which fails.

        restMMaterialMockMvc.perform(post("/api/m-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMaterial)))
            .andExpect(status().isBadRequest());

        List<MMaterial> mMaterialList = mMaterialRepository.findAll();
        assertThat(mMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMMaterials() throws Exception {
        // Initialize the database
        mMaterialRepository.saveAndFlush(mMaterial);

        // Get all the mMaterialList
        restMMaterialMockMvc.perform(get("/api/m-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].harga").value(hasItem(DEFAULT_HARGA.doubleValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMMaterial() throws Exception {
        // Initialize the database
        mMaterialRepository.saveAndFlush(mMaterial);

        // Get the mMaterial
        restMMaterialMockMvc.perform(get("/api/m-materials/{id}", mMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mMaterial.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.harga").value(DEFAULT_HARGA.doubleValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMMaterial() throws Exception {
        // Get the mMaterial
        restMMaterialMockMvc.perform(get("/api/m-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMMaterial() throws Exception {
        // Initialize the database
        mMaterialService.save(mMaterial);

        int databaseSizeBeforeUpdate = mMaterialRepository.findAll().size();

        // Update the mMaterial
        MMaterial updatedMMaterial = mMaterialRepository.findById(mMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedMMaterial are not directly saved in db
        em.detach(updatedMMaterial);
        updatedMMaterial
            .nama(UPDATED_NAMA)
            .deskripsi(UPDATED_DESKRIPSI)
            .harga(UPDATED_HARGA)
            .qty(UPDATED_QTY)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMMaterialMockMvc.perform(put("/api/m-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMMaterial)))
            .andExpect(status().isOk());

        // Validate the MMaterial in the database
        List<MMaterial> mMaterialList = mMaterialRepository.findAll();
        assertThat(mMaterialList).hasSize(databaseSizeBeforeUpdate);
        MMaterial testMMaterial = mMaterialList.get(mMaterialList.size() - 1);
        assertThat(testMMaterial.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMMaterial.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMMaterial.getHarga()).isEqualTo(UPDATED_HARGA);
        assertThat(testMMaterial.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testMMaterial.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMMaterial.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMMaterial() throws Exception {
        int databaseSizeBeforeUpdate = mMaterialRepository.findAll().size();

        // Create the MMaterial

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMaterialMockMvc.perform(put("/api/m-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the MMaterial in the database
        List<MMaterial> mMaterialList = mMaterialRepository.findAll();
        assertThat(mMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMMaterial() throws Exception {
        // Initialize the database
        mMaterialService.save(mMaterial);

        int databaseSizeBeforeDelete = mMaterialRepository.findAll().size();

        // Get the mMaterial
        restMMaterialMockMvc.perform(delete("/api/m-materials/{id}", mMaterial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MMaterial> mMaterialList = mMaterialRepository.findAll();
        assertThat(mMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MMaterial.class);
        MMaterial mMaterial1 = new MMaterial();
        mMaterial1.setId(1L);
        MMaterial mMaterial2 = new MMaterial();
        mMaterial2.setId(mMaterial1.getId());
        assertThat(mMaterial1).isEqualTo(mMaterial2);
        mMaterial2.setId(2L);
        assertThat(mMaterial1).isNotEqualTo(mMaterial2);
        mMaterial1.setId(null);
        assertThat(mMaterial1).isNotEqualTo(mMaterial2);
    }
}

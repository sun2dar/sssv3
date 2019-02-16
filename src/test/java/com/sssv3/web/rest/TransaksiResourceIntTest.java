package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.Transaksi;
import com.sssv3.repository.TransaksiRepository;
import com.sssv3.service.TransaksiService;
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

import com.sssv3.domain.enumeration.TransaksiType;
import com.sssv3.domain.enumeration.TransaksiCategory;
import com.sssv3.domain.enumeration.Status;
/**
 * Test class for the TransaksiResource REST controller.
 *
 * @see TransaksiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class TransaksiResourceIntTest {

    private static final TransaksiType DEFAULT_TIPE = TransaksiType.PEMBELIAN;
    private static final TransaksiType UPDATED_TIPE = TransaksiType.PENJUALAN;

    private static final TransaksiCategory DEFAULT_CATEGORY = TransaksiCategory.LOG;
    private static final TransaksiCategory UPDATED_CATEGORY = TransaksiCategory.VENEER;

    private static final LocalDate DEFAULT_TANGGAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TANGGAL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INVOICENO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICENO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_INVOICEFILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_INVOICEFILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_INVOICEFILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_INVOICEFILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOPOL = "AAAAAAAAAA";
    private static final String UPDATED_NOPOL = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransaksiMockMvc;

    private Transaksi transaksi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransaksiResource transaksiResource = new TransaksiResource(transaksiService);
        this.restTransaksiMockMvc = MockMvcBuilders.standaloneSetup(transaksiResource)
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
    public static Transaksi createEntity(EntityManager em) {
        Transaksi transaksi = new Transaksi()
            .tipe(DEFAULT_TIPE)
            .category(DEFAULT_CATEGORY)
            .tanggal(DEFAULT_TANGGAL)
            .invoiceno(DEFAULT_INVOICENO)
            .invoicefile(DEFAULT_INVOICEFILE)
            .invoicefileContentType(DEFAULT_INVOICEFILE_CONTENT_TYPE)
            .nopol(DEFAULT_NOPOL)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .deskripsi(DEFAULT_DESKRIPSI);
        return transaksi;
    }

    @Before
    public void initTest() {
        transaksi = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaksi() throws Exception {
        int databaseSizeBeforeCreate = transaksiRepository.findAll().size();

        // Create the Transaksi
        restTransaksiMockMvc.perform(post("/api/transaksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaksi)))
            .andExpect(status().isCreated());

        // Validate the Transaksi in the database
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeCreate + 1);
        Transaksi testTransaksi = transaksiList.get(transaksiList.size() - 1);
        assertThat(testTransaksi.getTipe()).isEqualTo(DEFAULT_TIPE);
        assertThat(testTransaksi.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testTransaksi.getTanggal()).isEqualTo(DEFAULT_TANGGAL);
        assertThat(testTransaksi.getInvoiceno()).isEqualTo(DEFAULT_INVOICENO);
        assertThat(testTransaksi.getInvoicefile()).isEqualTo(DEFAULT_INVOICEFILE);
        assertThat(testTransaksi.getInvoicefileContentType()).isEqualTo(DEFAULT_INVOICEFILE_CONTENT_TYPE);
        assertThat(testTransaksi.getNopol()).isEqualTo(DEFAULT_NOPOL);
        assertThat(testTransaksi.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransaksi.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTransaksi.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
    }

    @Test
    @Transactional
    public void createTransaksiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transaksiRepository.findAll().size();

        // Create the Transaksi with an existing ID
        transaksi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransaksiMockMvc.perform(post("/api/transaksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaksi)))
            .andExpect(status().isBadRequest());

        // Validate the Transaksi in the database
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTanggalIsRequired() throws Exception {
        int databaseSizeBeforeTest = transaksiRepository.findAll().size();
        // set the field null
        transaksi.setTanggal(null);

        // Create the Transaksi, which fails.

        restTransaksiMockMvc.perform(post("/api/transaksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaksi)))
            .andExpect(status().isBadRequest());

        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransaksis() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList
        restTransaksiMockMvc.perform(get("/api/transaksis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaksi.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipe").value(hasItem(DEFAULT_TIPE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].tanggal").value(hasItem(DEFAULT_TANGGAL.toString())))
            .andExpect(jsonPath("$.[*].invoiceno").value(hasItem(DEFAULT_INVOICENO.toString())))
            .andExpect(jsonPath("$.[*].invoicefileContentType").value(hasItem(DEFAULT_INVOICEFILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoicefile").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICEFILE))))
            .andExpect(jsonPath("$.[*].nopol").value(hasItem(DEFAULT_NOPOL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())));
    }
    
    @Test
    @Transactional
    public void getTransaksi() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get the transaksi
        restTransaksiMockMvc.perform(get("/api/transaksis/{id}", transaksi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaksi.getId().intValue()))
            .andExpect(jsonPath("$.tipe").value(DEFAULT_TIPE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.tanggal").value(DEFAULT_TANGGAL.toString()))
            .andExpect(jsonPath("$.invoiceno").value(DEFAULT_INVOICENO.toString()))
            .andExpect(jsonPath("$.invoicefileContentType").value(DEFAULT_INVOICEFILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.invoicefile").value(Base64Utils.encodeToString(DEFAULT_INVOICEFILE)))
            .andExpect(jsonPath("$.nopol").value(DEFAULT_NOPOL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransaksi() throws Exception {
        // Get the transaksi
        restTransaksiMockMvc.perform(get("/api/transaksis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaksi() throws Exception {
        // Initialize the database
        transaksiService.save(transaksi);

        int databaseSizeBeforeUpdate = transaksiRepository.findAll().size();

        // Update the transaksi
        Transaksi updatedTransaksi = transaksiRepository.findById(transaksi.getId()).get();
        // Disconnect from session so that the updates on updatedTransaksi are not directly saved in db
        em.detach(updatedTransaksi);
        updatedTransaksi
            .tipe(UPDATED_TIPE)
            .category(UPDATED_CATEGORY)
            .tanggal(UPDATED_TANGGAL)
            .invoiceno(UPDATED_INVOICENO)
            .invoicefile(UPDATED_INVOICEFILE)
            .invoicefileContentType(UPDATED_INVOICEFILE_CONTENT_TYPE)
            .nopol(UPDATED_NOPOL)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .deskripsi(UPDATED_DESKRIPSI);

        restTransaksiMockMvc.perform(put("/api/transaksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaksi)))
            .andExpect(status().isOk());

        // Validate the Transaksi in the database
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeUpdate);
        Transaksi testTransaksi = transaksiList.get(transaksiList.size() - 1);
        assertThat(testTransaksi.getTipe()).isEqualTo(UPDATED_TIPE);
        assertThat(testTransaksi.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTransaksi.getTanggal()).isEqualTo(UPDATED_TANGGAL);
        assertThat(testTransaksi.getInvoiceno()).isEqualTo(UPDATED_INVOICENO);
        assertThat(testTransaksi.getInvoicefile()).isEqualTo(UPDATED_INVOICEFILE);
        assertThat(testTransaksi.getInvoicefileContentType()).isEqualTo(UPDATED_INVOICEFILE_CONTENT_TYPE);
        assertThat(testTransaksi.getNopol()).isEqualTo(UPDATED_NOPOL);
        assertThat(testTransaksi.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransaksi.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTransaksi.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaksi() throws Exception {
        int databaseSizeBeforeUpdate = transaksiRepository.findAll().size();

        // Create the Transaksi

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransaksiMockMvc.perform(put("/api/transaksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaksi)))
            .andExpect(status().isBadRequest());

        // Validate the Transaksi in the database
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransaksi() throws Exception {
        // Initialize the database
        transaksiService.save(transaksi);

        int databaseSizeBeforeDelete = transaksiRepository.findAll().size();

        // Get the transaksi
        restTransaksiMockMvc.perform(delete("/api/transaksis/{id}", transaksi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaksi.class);
        Transaksi transaksi1 = new Transaksi();
        transaksi1.setId(1L);
        Transaksi transaksi2 = new Transaksi();
        transaksi2.setId(transaksi1.getId());
        assertThat(transaksi1).isEqualTo(transaksi2);
        transaksi2.setId(2L);
        assertThat(transaksi1).isNotEqualTo(transaksi2);
        transaksi1.setId(null);
        assertThat(transaksi1).isNotEqualTo(transaksi2);
    }
}

package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MTeam;
import com.sssv3.repository.MTeamRepository;
import com.sssv3.service.MTeamService;
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
 * Test class for the MTeamResource REST controller.
 *
 * @see MTeamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MTeamResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final Integer DEFAULT_JUMLAH = 1;
    private static final Integer UPDATED_JUMLAH = 2;

    private static final String DEFAULT_DESKRIPSI = "AAAAAAAAAA";
    private static final String UPDATED_DESKRIPSI = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MTeamRepository mTeamRepository;

    @Autowired
    private MTeamService mTeamService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMTeamMockMvc;

    private MTeam mTeam;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MTeamResource mTeamResource = new MTeamResource(mTeamService);
        this.restMTeamMockMvc = MockMvcBuilders.standaloneSetup(mTeamResource)
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
    public static MTeam createEntity(EntityManager em) {
        MTeam mTeam = new MTeam()
            .nama(DEFAULT_NAMA)
            .jumlah(DEFAULT_JUMLAH)
            .deskripsi(DEFAULT_DESKRIPSI)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON);
        return mTeam;
    }

    @Before
    public void initTest() {
        mTeam = createEntity(em);
    }

    @Test
    @Transactional
    public void createMTeam() throws Exception {
        int databaseSizeBeforeCreate = mTeamRepository.findAll().size();

        // Create the MTeam
        restMTeamMockMvc.perform(post("/api/m-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mTeam)))
            .andExpect(status().isCreated());

        // Validate the MTeam in the database
        List<MTeam> mTeamList = mTeamRepository.findAll();
        assertThat(mTeamList).hasSize(databaseSizeBeforeCreate + 1);
        MTeam testMTeam = mTeamList.get(mTeamList.size() - 1);
        assertThat(testMTeam.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMTeam.getJumlah()).isEqualTo(DEFAULT_JUMLAH);
        assertThat(testMTeam.getDeskripsi()).isEqualTo(DEFAULT_DESKRIPSI);
        assertThat(testMTeam.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMTeam.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createMTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mTeamRepository.findAll().size();

        // Create the MTeam with an existing ID
        mTeam.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMTeamMockMvc.perform(post("/api/m-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mTeam)))
            .andExpect(status().isBadRequest());

        // Validate the MTeam in the database
        List<MTeam> mTeamList = mTeamRepository.findAll();
        assertThat(mTeamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mTeamRepository.findAll().size();
        // set the field null
        mTeam.setNama(null);

        // Create the MTeam, which fails.

        restMTeamMockMvc.perform(post("/api/m-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mTeam)))
            .andExpect(status().isBadRequest());

        List<MTeam> mTeamList = mTeamRepository.findAll();
        assertThat(mTeamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMTeams() throws Exception {
        // Initialize the database
        mTeamRepository.saveAndFlush(mTeam);

        // Get all the mTeamList
        restMTeamMockMvc.perform(get("/api/m-teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].jumlah").value(hasItem(DEFAULT_JUMLAH)))
            .andExpect(jsonPath("$.[*].deskripsi").value(hasItem(DEFAULT_DESKRIPSI.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMTeam() throws Exception {
        // Initialize the database
        mTeamRepository.saveAndFlush(mTeam);

        // Get the mTeam
        restMTeamMockMvc.perform(get("/api/m-teams/{id}", mTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mTeam.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.jumlah").value(DEFAULT_JUMLAH))
            .andExpect(jsonPath("$.deskripsi").value(DEFAULT_DESKRIPSI.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMTeam() throws Exception {
        // Get the mTeam
        restMTeamMockMvc.perform(get("/api/m-teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMTeam() throws Exception {
        // Initialize the database
        mTeamService.save(mTeam);

        int databaseSizeBeforeUpdate = mTeamRepository.findAll().size();

        // Update the mTeam
        MTeam updatedMTeam = mTeamRepository.findById(mTeam.getId()).get();
        // Disconnect from session so that the updates on updatedMTeam are not directly saved in db
        em.detach(updatedMTeam);
        updatedMTeam
            .nama(UPDATED_NAMA)
            .jumlah(UPDATED_JUMLAH)
            .deskripsi(UPDATED_DESKRIPSI)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON);

        restMTeamMockMvc.perform(put("/api/m-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMTeam)))
            .andExpect(status().isOk());

        // Validate the MTeam in the database
        List<MTeam> mTeamList = mTeamRepository.findAll();
        assertThat(mTeamList).hasSize(databaseSizeBeforeUpdate);
        MTeam testMTeam = mTeamList.get(mTeamList.size() - 1);
        assertThat(testMTeam.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMTeam.getJumlah()).isEqualTo(UPDATED_JUMLAH);
        assertThat(testMTeam.getDeskripsi()).isEqualTo(UPDATED_DESKRIPSI);
        assertThat(testMTeam.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMTeam.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMTeam() throws Exception {
        int databaseSizeBeforeUpdate = mTeamRepository.findAll().size();

        // Create the MTeam

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMTeamMockMvc.perform(put("/api/m-teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mTeam)))
            .andExpect(status().isBadRequest());

        // Validate the MTeam in the database
        List<MTeam> mTeamList = mTeamRepository.findAll();
        assertThat(mTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMTeam() throws Exception {
        // Initialize the database
        mTeamService.save(mTeam);

        int databaseSizeBeforeDelete = mTeamRepository.findAll().size();

        // Get the mTeam
        restMTeamMockMvc.perform(delete("/api/m-teams/{id}", mTeam.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MTeam> mTeamList = mTeamRepository.findAll();
        assertThat(mTeamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MTeam.class);
        MTeam mTeam1 = new MTeam();
        mTeam1.setId(1L);
        MTeam mTeam2 = new MTeam();
        mTeam2.setId(mTeam1.getId());
        assertThat(mTeam1).isEqualTo(mTeam2);
        mTeam2.setId(2L);
        assertThat(mTeam1).isNotEqualTo(mTeam2);
        mTeam1.setId(null);
        assertThat(mTeam1).isNotEqualTo(mTeam2);
    }
}

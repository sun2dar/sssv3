package com.sssv3.web.rest;

import com.sssv3.Ssv3App;

import com.sssv3.domain.MMessage;
import com.sssv3.repository.MMessageRepository;
import com.sssv3.service.MMessageService;
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

import com.sssv3.domain.enumeration.Status;
/**
 * Test class for the MMessageResource REST controller.
 *
 * @see MMessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ssv3App.class)
public class MMessageResourceIntTest {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACT;
    private static final Status UPDATED_STATUS = Status.DIS;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MMessageRepository mMessageRepository;

    @Autowired
    private MMessageService mMessageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMMessageMockMvc;

    private MMessage mMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MMessageResource mMessageResource = new MMessageResource(mMessageService);
        this.restMMessageMockMvc = MockMvcBuilders.standaloneSetup(mMessageResource)
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
    public static MMessage createEntity(EntityManager em) {
        MMessage mMessage = new MMessage()
            .message(DEFAULT_MESSAGE)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return mMessage;
    }

    @Before
    public void initTest() {
        mMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createMMessage() throws Exception {
        int databaseSizeBeforeCreate = mMessageRepository.findAll().size();

        // Create the MMessage
        restMMessageMockMvc.perform(post("/api/m-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMessage)))
            .andExpect(status().isCreated());

        // Validate the MMessage in the database
        List<MMessage> mMessageList = mMessageRepository.findAll();
        assertThat(mMessageList).hasSize(databaseSizeBeforeCreate + 1);
        MMessage testMMessage = mMessageList.get(mMessageList.size() - 1);
        assertThat(testMMessage.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testMMessage.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMMessage.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMMessage.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void createMMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mMessageRepository.findAll().size();

        // Create the MMessage with an existing ID
        mMessage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMMessageMockMvc.perform(post("/api/m-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMessage)))
            .andExpect(status().isBadRequest());

        // Validate the MMessage in the database
        List<MMessage> mMessageList = mMessageRepository.findAll();
        assertThat(mMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMMessages() throws Exception {
        // Initialize the database
        mMessageRepository.saveAndFlush(mMessage);

        // Get all the mMessageList
        restMMessageMockMvc.perform(get("/api/m-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMMessage() throws Exception {
        // Initialize the database
        mMessageRepository.saveAndFlush(mMessage);

        // Get the mMessage
        restMMessageMockMvc.perform(get("/api/m-messages/{id}", mMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mMessage.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMMessage() throws Exception {
        // Get the mMessage
        restMMessageMockMvc.perform(get("/api/m-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMMessage() throws Exception {
        // Initialize the database
        mMessageService.save(mMessage);

        int databaseSizeBeforeUpdate = mMessageRepository.findAll().size();

        // Update the mMessage
        MMessage updatedMMessage = mMessageRepository.findById(mMessage.getId()).get();
        // Disconnect from session so that the updates on updatedMMessage are not directly saved in db
        em.detach(updatedMMessage);
        updatedMMessage
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .modifiedOn(UPDATED_MODIFIED_ON);

        restMMessageMockMvc.perform(put("/api/m-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMMessage)))
            .andExpect(status().isOk());

        // Validate the MMessage in the database
        List<MMessage> mMessageList = mMessageRepository.findAll();
        assertThat(mMessageList).hasSize(databaseSizeBeforeUpdate);
        MMessage testMMessage = mMessageList.get(mMessageList.size() - 1);
        assertThat(testMMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testMMessage.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMMessage.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMMessage.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingMMessage() throws Exception {
        int databaseSizeBeforeUpdate = mMessageRepository.findAll().size();

        // Create the MMessage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMessageMockMvc.perform(put("/api/m-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mMessage)))
            .andExpect(status().isBadRequest());

        // Validate the MMessage in the database
        List<MMessage> mMessageList = mMessageRepository.findAll();
        assertThat(mMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMMessage() throws Exception {
        // Initialize the database
        mMessageService.save(mMessage);

        int databaseSizeBeforeDelete = mMessageRepository.findAll().size();

        // Get the mMessage
        restMMessageMockMvc.perform(delete("/api/m-messages/{id}", mMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MMessage> mMessageList = mMessageRepository.findAll();
        assertThat(mMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MMessage.class);
        MMessage mMessage1 = new MMessage();
        mMessage1.setId(1L);
        MMessage mMessage2 = new MMessage();
        mMessage2.setId(mMessage1.getId());
        assertThat(mMessage1).isEqualTo(mMessage2);
        mMessage2.setId(2L);
        assertThat(mMessage1).isNotEqualTo(mMessage2);
        mMessage1.setId(null);
        assertThat(mMessage1).isNotEqualTo(mMessage2);
    }
}

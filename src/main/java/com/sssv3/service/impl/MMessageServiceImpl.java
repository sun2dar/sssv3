package com.sssv3.service.impl;

import com.sssv3.service.MMessageService;
import com.sssv3.domain.MMessage;
import com.sssv3.repository.MMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MMessage.
 */
@Service
@Transactional
public class MMessageServiceImpl implements MMessageService {

    private final Logger log = LoggerFactory.getLogger(MMessageServiceImpl.class);

    private final MMessageRepository mMessageRepository;

    public MMessageServiceImpl(MMessageRepository mMessageRepository) {
        this.mMessageRepository = mMessageRepository;
    }

    /**
     * Save a mMessage.
     *
     * @param mMessage the entity to save
     * @return the persisted entity
     */
    @Override
    public MMessage save(MMessage mMessage) {
        log.debug("Request to save MMessage : {}", mMessage);
        return mMessageRepository.save(mMessage);
    }

    /**
     * Get all the mMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MMessage> findAll(Pageable pageable) {
        log.debug("Request to get all MMessages");
        return mMessageRepository.findAll(pageable);
    }


    /**
     * Get one mMessage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MMessage> findOne(Long id) {
        log.debug("Request to get MMessage : {}", id);
        return mMessageRepository.findById(id);
    }

    /**
     * Delete the mMessage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MMessage : {}", id);
        mMessageRepository.deleteById(id);
    }
}

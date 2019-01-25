package com.sssv3.service.impl;

import com.sssv3.service.MLogService;
import com.sssv3.domain.MLog;
import com.sssv3.repository.MLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MLog.
 */
@Service
@Transactional
public class MLogServiceImpl implements MLogService {

    private final Logger log = LoggerFactory.getLogger(MLogServiceImpl.class);

    private final MLogRepository mLogRepository;

    public MLogServiceImpl(MLogRepository mLogRepository) {
        this.mLogRepository = mLogRepository;
    }

    /**
     * Save a mLog.
     *
     * @param mLog the entity to save
     * @return the persisted entity
     */
    @Override
    public MLog save(MLog mLog) {
        log.debug("Request to save MLog : {}", mLog);
        return mLogRepository.save(mLog);
    }

    /**
     * Get all the mLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MLog> findAll(Pageable pageable) {
        log.debug("Request to get all MLogs");
        return mLogRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MLog> findByNama(String nama, Pageable pageable){
        log.debug("Request to get all MLogs");
        return mLogRepository.findByNamaContainingIgnoreCase(nama, pageable);
    }

    /**
     * Get one mLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MLog> findOne(Long id) {
        log.debug("Request to get MLog : {}", id);
        return mLogRepository.findById(id);
    }

    /**
     * Delete the mLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MLog : {}", id);
        mLogRepository.deleteById(id);
    }
}

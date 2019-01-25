package com.sssv3.service.impl;

import com.sssv3.service.TLogService;
import com.sssv3.domain.TLog;
import com.sssv3.repository.TLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TLog.
 */
@Service
@Transactional
public class TLogServiceImpl implements TLogService {

    private final Logger log = LoggerFactory.getLogger(TLogServiceImpl.class);

    private final TLogRepository tLogRepository;

    public TLogServiceImpl(TLogRepository tLogRepository) {
        this.tLogRepository = tLogRepository;
    }

    /**
     * Save a tLog.
     *
     * @param tLog the entity to save
     * @return the persisted entity
     */
    @Override
    public TLog save(TLog tLog) {
        log.debug("Request to save TLog : {}", tLog);
        return tLogRepository.save(tLog);
    }

    /**
     * Get all the tLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TLog> findAll(Pageable pageable) {
        log.debug("Request to get all TLogs");
        return tLogRepository.findAll(pageable);
    }


    /**
     * Get one tLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TLog> findOne(Long id) {
        log.debug("Request to get TLog : {}", id);
        return tLogRepository.findById(id);
    }

    /**
     * Delete the tLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TLog : {}", id);
        tLogRepository.deleteById(id);
    }
}

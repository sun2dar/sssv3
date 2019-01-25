package com.sssv3.service.impl;

import com.sssv3.service.TOperasionalService;
import com.sssv3.domain.TOperasional;
import com.sssv3.repository.TOperasionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TOperasional.
 */
@Service
@Transactional
public class TOperasionalServiceImpl implements TOperasionalService {

    private final Logger log = LoggerFactory.getLogger(TOperasionalServiceImpl.class);

    private final TOperasionalRepository tOperasionalRepository;

    public TOperasionalServiceImpl(TOperasionalRepository tOperasionalRepository) {
        this.tOperasionalRepository = tOperasionalRepository;
    }

    /**
     * Save a tOperasional.
     *
     * @param tOperasional the entity to save
     * @return the persisted entity
     */
    @Override
    public TOperasional save(TOperasional tOperasional) {
        log.debug("Request to save TOperasional : {}", tOperasional);
        return tOperasionalRepository.save(tOperasional);
    }

    /**
     * Get all the tOperasionals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TOperasional> findAll(Pageable pageable) {
        log.debug("Request to get all TOperasionals");
        return tOperasionalRepository.findAll(pageable);
    }


    /**
     * Get one tOperasional by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TOperasional> findOne(Long id) {
        log.debug("Request to get TOperasional : {}", id);
        return tOperasionalRepository.findById(id);
    }

    /**
     * Delete the tOperasional by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TOperasional : {}", id);
        tOperasionalRepository.deleteById(id);
    }
}

package com.sssv3.service.impl;

import com.sssv3.service.TBongkarService;
import com.sssv3.domain.TBongkar;
import com.sssv3.repository.TBongkarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TBongkar.
 */
@Service
@Transactional
public class TBongkarServiceImpl implements TBongkarService {

    private final Logger log = LoggerFactory.getLogger(TBongkarServiceImpl.class);

    private final TBongkarRepository tBongkarRepository;

    public TBongkarServiceImpl(TBongkarRepository tBongkarRepository) {
        this.tBongkarRepository = tBongkarRepository;
    }

    /**
     * Save a tBongkar.
     *
     * @param tBongkar the entity to save
     * @return the persisted entity
     */
    @Override
    public TBongkar save(TBongkar tBongkar) {
        log.debug("Request to save TBongkar : {}", tBongkar);
        return tBongkarRepository.save(tBongkar);
    }

    /**
     * Get all the tBongkars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TBongkar> findAll(Pageable pageable) {
        log.debug("Request to get all TBongkars");
        return tBongkarRepository.findAll(pageable);
    }


    /**
     * Get one tBongkar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TBongkar> findOne(Long id) {
        log.debug("Request to get TBongkar : {}", id);
        return tBongkarRepository.findById(id);
    }

    /**
     * Delete the tBongkar by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TBongkar : {}", id);
        tBongkarRepository.deleteById(id);
    }
}

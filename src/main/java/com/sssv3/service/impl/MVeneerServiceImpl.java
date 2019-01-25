package com.sssv3.service.impl;

import com.sssv3.service.MVeneerService;
import com.sssv3.domain.MVeneer;
import com.sssv3.repository.MVeneerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MVeneer.
 */
@Service
@Transactional
public class MVeneerServiceImpl implements MVeneerService {

    private final Logger log = LoggerFactory.getLogger(MVeneerServiceImpl.class);

    private final MVeneerRepository mVeneerRepository;

    public MVeneerServiceImpl(MVeneerRepository mVeneerRepository) {
        this.mVeneerRepository = mVeneerRepository;
    }

    /**
     * Save a mVeneer.
     *
     * @param mVeneer the entity to save
     * @return the persisted entity
     */
    @Override
    public MVeneer save(MVeneer mVeneer) {
        log.debug("Request to save MVeneer : {}", mVeneer);
        return mVeneerRepository.save(mVeneer);
    }

    /**
     * Get all the mVeneers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MVeneer> findAll(Pageable pageable) {
        log.debug("Request to get all MVeneers");
        return mVeneerRepository.findAll(pageable);
    }


    /**
     * Get one mVeneer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MVeneer> findOne(Long id) {
        log.debug("Request to get MVeneer : {}", id);
        return mVeneerRepository.findById(id);
    }

    /**
     * Delete the mVeneer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MVeneer : {}", id);
        mVeneerRepository.deleteById(id);
    }
}

package com.sssv3.service.impl;

import com.sssv3.service.MPajakService;
import com.sssv3.domain.MPajak;
import com.sssv3.repository.MPajakRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MPajak.
 */
@Service
@Transactional
public class MPajakServiceImpl implements MPajakService {

    private final Logger log = LoggerFactory.getLogger(MPajakServiceImpl.class);

    private final MPajakRepository mPajakRepository;

    public MPajakServiceImpl(MPajakRepository mPajakRepository) {
        this.mPajakRepository = mPajakRepository;
    }

    /**
     * Save a mPajak.
     *
     * @param mPajak the entity to save
     * @return the persisted entity
     */
    @Override
    public MPajak save(MPajak mPajak) {
        log.debug("Request to save MPajak : {}", mPajak);
        return mPajakRepository.save(mPajak);
    }

    /**
     * Get all the mPajaks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MPajak> findAll(Pageable pageable) {
        log.debug("Request to get all MPajaks");
        return mPajakRepository.findAll(pageable);
    }


    /**
     * Get one mPajak by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MPajak> findOne(Long id) {
        log.debug("Request to get MPajak : {}", id);
        return mPajakRepository.findById(id);
    }

    /**
     * Delete the mPajak by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MPajak : {}", id);
        mPajakRepository.deleteById(id);
    }
}

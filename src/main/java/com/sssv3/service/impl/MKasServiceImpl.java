package com.sssv3.service.impl;

import com.sssv3.service.MKasService;
import com.sssv3.domain.MKas;
import com.sssv3.repository.MKasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MKas.
 */
@Service
@Transactional
public class MKasServiceImpl implements MKasService {

    private final Logger log = LoggerFactory.getLogger(MKasServiceImpl.class);

    private final MKasRepository mKasRepository;

    public MKasServiceImpl(MKasRepository mKasRepository) {
        this.mKasRepository = mKasRepository;
    }

    /**
     * Save a mKas.
     *
     * @param mKas the entity to save
     * @return the persisted entity
     */
    @Override
    public MKas save(MKas mKas) {
        log.debug("Request to save MKas : {}", mKas);
        return mKasRepository.save(mKas);
    }

    /**
     * Get all the mKas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MKas> findAll(Pageable pageable) {
        log.debug("Request to get all MKas");
        return mKasRepository.findAll(pageable);
    }


    /**
     * Get one mKas by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MKas> findOne(Long id) {
        log.debug("Request to get MKas : {}", id);
        return mKasRepository.findById(id);
    }

    /**
     * Delete the mKas by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MKas : {}", id);
        mKasRepository.deleteById(id);
    }
}

package com.sssv3.service.impl;

import com.sssv3.service.TKasService;
import com.sssv3.domain.TKas;
import com.sssv3.repository.TKasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TKas.
 */
@Service
@Transactional
public class TKasServiceImpl implements TKasService {

    private final Logger log = LoggerFactory.getLogger(TKasServiceImpl.class);

    private final TKasRepository tKasRepository;

    public TKasServiceImpl(TKasRepository tKasRepository) {
        this.tKasRepository = tKasRepository;
    }

    /**
     * Save a tKas.
     *
     * @param tKas the entity to save
     * @return the persisted entity
     */
    @Override
    public TKas save(TKas tKas) {
        log.debug("Request to save TKas : {}", tKas);
        return tKasRepository.save(tKas);
    }

    /**
     * Get all the tKas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TKas> findAll(Pageable pageable) {
        log.debug("Request to get all TKas");
        return tKasRepository.findAll(pageable);
    }


    /**
     * Get one tKas by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TKas> findOne(Long id) {
        log.debug("Request to get TKas : {}", id);
        return tKasRepository.findById(id);
    }

    /**
     * Delete the tKas by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TKas : {}", id);
        tKasRepository.deleteById(id);
    }
}

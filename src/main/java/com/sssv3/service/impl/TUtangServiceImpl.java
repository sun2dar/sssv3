package com.sssv3.service.impl;

import com.sssv3.service.TUtangService;
import com.sssv3.domain.TUtang;
import com.sssv3.repository.TUtangRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TUtang.
 */
@Service
@Transactional
public class TUtangServiceImpl implements TUtangService {

    private final Logger log = LoggerFactory.getLogger(TUtangServiceImpl.class);

    private final TUtangRepository tUtangRepository;

    public TUtangServiceImpl(TUtangRepository tUtangRepository) {
        this.tUtangRepository = tUtangRepository;
    }

    /**
     * Save a tUtang.
     *
     * @param tUtang the entity to save
     * @return the persisted entity
     */
    @Override
    public TUtang save(TUtang tUtang) {
        log.debug("Request to save TUtang : {}", tUtang);
        return tUtangRepository.save(tUtang);
    }

    /**
     * Get all the tUtangs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TUtang> findAll(Pageable pageable) {
        log.debug("Request to get all TUtangs");
        return tUtangRepository.findAll(pageable);
    }


    /**
     * Get one tUtang by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TUtang> findOne(Long id) {
        log.debug("Request to get TUtang : {}", id);
        return tUtangRepository.findById(id);
    }

    /**
     * Delete the tUtang by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TUtang : {}", id);
        tUtangRepository.deleteById(id);
    }
}

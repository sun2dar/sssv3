package com.sssv3.service.impl;

import com.sssv3.service.TPlywoodService;
import com.sssv3.domain.TPlywood;
import com.sssv3.repository.TPlywoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TPlywood.
 */
@Service
@Transactional
public class TPlywoodServiceImpl implements TPlywoodService {

    private final Logger log = LoggerFactory.getLogger(TPlywoodServiceImpl.class);

    private final TPlywoodRepository tPlywoodRepository;

    public TPlywoodServiceImpl(TPlywoodRepository tPlywoodRepository) {
        this.tPlywoodRepository = tPlywoodRepository;
    }

    /**
     * Save a tPlywood.
     *
     * @param tPlywood the entity to save
     * @return the persisted entity
     */
    @Override
    public TPlywood save(TPlywood tPlywood) {
        log.debug("Request to save TPlywood : {}", tPlywood);
        return tPlywoodRepository.save(tPlywood);
    }

    /**
     * Get all the tPlywoods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TPlywood> findAll(Pageable pageable) {
        log.debug("Request to get all TPlywoods");
        return tPlywoodRepository.findAll(pageable);
    }


    /**
     * Get one tPlywood by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TPlywood> findOne(Long id) {
        log.debug("Request to get TPlywood : {}", id);
        return tPlywoodRepository.findById(id);
    }

    /**
     * Delete the tPlywood by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TPlywood : {}", id);
        tPlywoodRepository.deleteById(id);
    }
}

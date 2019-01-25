package com.sssv3.service.impl;

import com.sssv3.service.TVeneerService;
import com.sssv3.domain.TVeneer;
import com.sssv3.repository.TVeneerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TVeneer.
 */
@Service
@Transactional
public class TVeneerServiceImpl implements TVeneerService {

    private final Logger log = LoggerFactory.getLogger(TVeneerServiceImpl.class);

    private final TVeneerRepository tVeneerRepository;

    public TVeneerServiceImpl(TVeneerRepository tVeneerRepository) {
        this.tVeneerRepository = tVeneerRepository;
    }

    /**
     * Save a tVeneer.
     *
     * @param tVeneer the entity to save
     * @return the persisted entity
     */
    @Override
    public TVeneer save(TVeneer tVeneer) {
        log.debug("Request to save TVeneer : {}", tVeneer);
        return tVeneerRepository.save(tVeneer);
    }

    /**
     * Get all the tVeneers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TVeneer> findAll(Pageable pageable) {
        log.debug("Request to get all TVeneers");
        return tVeneerRepository.findAll(pageable);
    }


    /**
     * Get one tVeneer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TVeneer> findOne(Long id) {
        log.debug("Request to get TVeneer : {}", id);
        return tVeneerRepository.findById(id);
    }

    /**
     * Delete the tVeneer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TVeneer : {}", id);
        tVeneerRepository.deleteById(id);
    }
}

package com.sssv3.service.impl;

import com.sssv3.service.TMaterialService;
import com.sssv3.domain.TMaterial;
import com.sssv3.repository.TMaterialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TMaterial.
 */
@Service
@Transactional
public class TMaterialServiceImpl implements TMaterialService {

    private final Logger log = LoggerFactory.getLogger(TMaterialServiceImpl.class);

    private final TMaterialRepository tMaterialRepository;

    public TMaterialServiceImpl(TMaterialRepository tMaterialRepository) {
        this.tMaterialRepository = tMaterialRepository;
    }

    /**
     * Save a tMaterial.
     *
     * @param tMaterial the entity to save
     * @return the persisted entity
     */
    @Override
    public TMaterial save(TMaterial tMaterial) {
        log.debug("Request to save TMaterial : {}", tMaterial);
        return tMaterialRepository.save(tMaterial);
    }

    /**
     * Get all the tMaterials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TMaterial> findAll(Pageable pageable) {
        log.debug("Request to get all TMaterials");
        return tMaterialRepository.findAll(pageable);
    }


    /**
     * Get one tMaterial by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TMaterial> findOne(Long id) {
        log.debug("Request to get TMaterial : {}", id);
        return tMaterialRepository.findById(id);
    }

    /**
     * Delete the tMaterial by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TMaterial : {}", id);
        tMaterialRepository.deleteById(id);
    }
}

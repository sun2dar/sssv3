package com.sssv3.service.impl;

import com.sssv3.service.MMaterialService;
import com.sssv3.domain.MMaterial;
import com.sssv3.repository.MMaterialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MMaterial.
 */
@Service
@Transactional
public class MMaterialServiceImpl implements MMaterialService {

    private final Logger log = LoggerFactory.getLogger(MMaterialServiceImpl.class);

    private final MMaterialRepository mMaterialRepository;

    public MMaterialServiceImpl(MMaterialRepository mMaterialRepository) {
        this.mMaterialRepository = mMaterialRepository;
    }

    /**
     * Save a mMaterial.
     *
     * @param mMaterial the entity to save
     * @return the persisted entity
     */
    @Override
    public MMaterial save(MMaterial mMaterial) {
        log.debug("Request to save MMaterial : {}", mMaterial);
        System.out.println("TEST MATERIAL CASCADE CHILD");
        return mMaterialRepository.save(mMaterial);
    }

    /**
     * Get all the mMaterials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MMaterial> findAll(Pageable pageable) {
        log.debug("Request to get all MMaterials");
        return mMaterialRepository.findAll(pageable);
    }


    /**
     * Get one mMaterial by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MMaterial> findOne(Long id) {
        log.debug("Request to get MMaterial : {}", id);
        return mMaterialRepository.findById(id);
    }

    /**
     * Delete the mMaterial by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MMaterial : {}", id);
        mMaterialRepository.deleteById(id);
    }
}

package com.sssv3.service.impl;

import com.sssv3.service.MMaterialTypeService;
import com.sssv3.domain.MMaterialType;
import com.sssv3.repository.MMaterialTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MMaterialType.
 */
@Service
@Transactional
public class MMaterialTypeServiceImpl implements MMaterialTypeService {

    private final Logger log = LoggerFactory.getLogger(MMaterialTypeServiceImpl.class);

    private final MMaterialTypeRepository mMaterialTypeRepository;

    public MMaterialTypeServiceImpl(MMaterialTypeRepository mMaterialTypeRepository) {
        this.mMaterialTypeRepository = mMaterialTypeRepository;
    }

    /**
     * Save a mMaterialType.
     *
     * @param mMaterialType the entity to save
     * @return the persisted entity
     */
    @Override
    public MMaterialType save(MMaterialType mMaterialType) {
        log.debug("Request to save MMaterialType : {}", mMaterialType);
        System.out.println("TEST MATERIAL CASCADE MASTER");
        return mMaterialTypeRepository.save(mMaterialType);
    }

    /**
     * Get all the mMaterialTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MMaterialType> findAll(Pageable pageable) {
        log.debug("Request to get all MMaterialTypes");
        return mMaterialTypeRepository.findAll(pageable);
    }


    /**
     * Get one mMaterialType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MMaterialType> findOne(Long id) {
        log.debug("Request to get MMaterialType : {}", id);
        return mMaterialTypeRepository.findById(id);
    }

    /**
     * Delete the mMaterialType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MMaterialType : {}", id);
        mMaterialTypeRepository.deleteById(id);
    }
}

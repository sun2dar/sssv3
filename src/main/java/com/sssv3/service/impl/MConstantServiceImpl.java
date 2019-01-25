package com.sssv3.service.impl;

import com.sssv3.service.MConstantService;
import com.sssv3.domain.MConstant;
import com.sssv3.repository.MConstantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MConstant.
 */
@Service
@Transactional
public class MConstantServiceImpl implements MConstantService {

    private final Logger log = LoggerFactory.getLogger(MConstantServiceImpl.class);

    private final MConstantRepository mConstantRepository;

    public MConstantServiceImpl(MConstantRepository mConstantRepository) {
        this.mConstantRepository = mConstantRepository;
    }

    /**
     * Save a mConstant.
     *
     * @param mConstant the entity to save
     * @return the persisted entity
     */
    @Override
    public MConstant save(MConstant mConstant) {
        log.debug("Request to save MConstant : {}", mConstant);
        return mConstantRepository.save(mConstant);
    }

    /**
     * Get all the mConstants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MConstant> findAll(Pageable pageable) {
        log.debug("Request to get all MConstants");
        return mConstantRepository.findAll(pageable);
    }


    /**
     * Get one mConstant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MConstant> findOne(Long id) {
        log.debug("Request to get MConstant : {}", id);
        return mConstantRepository.findById(id);
    }

    /**
     * Delete the mConstant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MConstant : {}", id);
        mConstantRepository.deleteById(id);
    }
}

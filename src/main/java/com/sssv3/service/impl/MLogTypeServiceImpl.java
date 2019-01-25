package com.sssv3.service.impl;

import com.sssv3.service.MLogTypeService;
import com.sssv3.domain.MLogType;
import com.sssv3.repository.MLogTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MLogType.
 */
@Service
@Transactional
public class MLogTypeServiceImpl implements MLogTypeService {

    private final Logger log = LoggerFactory.getLogger(MLogTypeServiceImpl.class);

    private final MLogTypeRepository mLogTypeRepository;

    public MLogTypeServiceImpl(MLogTypeRepository mLogTypeRepository) {
        this.mLogTypeRepository = mLogTypeRepository;
    }

    /**
     * Save a mLogType.
     *
     * @param mLogType the entity to save
     * @return the persisted entity
     */
    @Override
    public MLogType save(MLogType mLogType) {
        log.debug("Request to save MLogType : {}", mLogType);
        return mLogTypeRepository.save(mLogType);
    }

    /**
     * Get all the mLogTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MLogType> findAll(Pageable pageable) {
        log.debug("Request to get all MLogTypes");
        return mLogTypeRepository.findAll(pageable);
    }


    /**
     * Get one mLogType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MLogType> findOne(Long id) {
        log.debug("Request to get MLogType : {}", id);
        return mLogTypeRepository.findById(id);
    }

    /**
     * Delete the mLogType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MLogType : {}", id);
        mLogTypeRepository.deleteById(id);
    }
}

package com.sssv3.service.impl;

import com.sssv3.service.MOperasionalTypeService;
import com.sssv3.domain.MOperasionalType;
import com.sssv3.repository.MOperasionalTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MOperasionalType.
 */
@Service
@Transactional
public class MOperasionalTypeServiceImpl implements MOperasionalTypeService {

    private final Logger log = LoggerFactory.getLogger(MOperasionalTypeServiceImpl.class);

    private final MOperasionalTypeRepository mOperasionalTypeRepository;

    public MOperasionalTypeServiceImpl(MOperasionalTypeRepository mOperasionalTypeRepository) {
        this.mOperasionalTypeRepository = mOperasionalTypeRepository;
    }

    /**
     * Save a mOperasionalType.
     *
     * @param mOperasionalType the entity to save
     * @return the persisted entity
     */
    @Override
    public MOperasionalType save(MOperasionalType mOperasionalType) {
        log.debug("Request to save MOperasionalType : {}", mOperasionalType);
        return mOperasionalTypeRepository.save(mOperasionalType);
    }

    /**
     * Get all the mOperasionalTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MOperasionalType> findAll(Pageable pageable) {
        log.debug("Request to get all MOperasionalTypes");
        return mOperasionalTypeRepository.findAll(pageable);
    }


    /**
     * Get one mOperasionalType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MOperasionalType> findOne(Long id) {
        log.debug("Request to get MOperasionalType : {}", id);
        return mOperasionalTypeRepository.findById(id);
    }

    /**
     * Delete the mOperasionalType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MOperasionalType : {}", id);
        mOperasionalTypeRepository.deleteById(id);
    }
}

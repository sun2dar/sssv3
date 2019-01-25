package com.sssv3.service.impl;

import com.sssv3.service.MShiftService;
import com.sssv3.domain.MShift;
import com.sssv3.repository.MShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MShift.
 */
@Service
@Transactional
public class MShiftServiceImpl implements MShiftService {

    private final Logger log = LoggerFactory.getLogger(MShiftServiceImpl.class);

    private final MShiftRepository mShiftRepository;

    public MShiftServiceImpl(MShiftRepository mShiftRepository) {
        this.mShiftRepository = mShiftRepository;
    }

    /**
     * Save a mShift.
     *
     * @param mShift the entity to save
     * @return the persisted entity
     */
    @Override
    public MShift save(MShift mShift) {
        log.debug("Request to save MShift : {}", mShift);
        return mShiftRepository.save(mShift);
    }

    /**
     * Get all the mShifts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MShift> findAll(Pageable pageable) {
        log.debug("Request to get all MShifts");
        return mShiftRepository.findAll(pageable);
    }


    /**
     * Get one mShift by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MShift> findOne(Long id) {
        log.debug("Request to get MShift : {}", id);
        return mShiftRepository.findById(id);
    }

    /**
     * Delete the mShift by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MShift : {}", id);
        mShiftRepository.deleteById(id);
    }
}

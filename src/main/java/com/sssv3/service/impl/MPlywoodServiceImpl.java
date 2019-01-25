package com.sssv3.service.impl;

import com.sssv3.service.MPlywoodService;
import com.sssv3.domain.MPlywood;
import com.sssv3.repository.MPlywoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MPlywood.
 */
@Service
@Transactional
public class MPlywoodServiceImpl implements MPlywoodService {

    private final Logger log = LoggerFactory.getLogger(MPlywoodServiceImpl.class);

    private final MPlywoodRepository mPlywoodRepository;

    public MPlywoodServiceImpl(MPlywoodRepository mPlywoodRepository) {
        this.mPlywoodRepository = mPlywoodRepository;
    }

    /**
     * Save a mPlywood.
     *
     * @param mPlywood the entity to save
     * @return the persisted entity
     */
    @Override
    public MPlywood save(MPlywood mPlywood) {
        log.debug("Request to save MPlywood : {}", mPlywood);
        return mPlywoodRepository.save(mPlywood);
    }

    /**
     * Get all the mPlywoods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MPlywood> findAll(Pageable pageable) {
        log.debug("Request to get all MPlywoods");
        return mPlywoodRepository.findAll(pageable);
    }


    /**
     * Get one mPlywood by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MPlywood> findOne(Long id) {
        log.debug("Request to get MPlywood : {}", id);
        return mPlywoodRepository.findById(id);
    }

    /**
     * Delete the mPlywood by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MPlywood : {}", id);
        mPlywoodRepository.deleteById(id);
    }
}

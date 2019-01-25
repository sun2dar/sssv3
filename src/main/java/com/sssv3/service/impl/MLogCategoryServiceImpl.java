package com.sssv3.service.impl;

import com.sssv3.service.MLogCategoryService;
import com.sssv3.domain.MLogCategory;
import com.sssv3.repository.MLogCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MLogCategory.
 */
@Service
@Transactional
public class MLogCategoryServiceImpl implements MLogCategoryService {

    private final Logger log = LoggerFactory.getLogger(MLogCategoryServiceImpl.class);

    private final MLogCategoryRepository mLogCategoryRepository;

    public MLogCategoryServiceImpl(MLogCategoryRepository mLogCategoryRepository) {
        this.mLogCategoryRepository = mLogCategoryRepository;
    }

    /**
     * Save a mLogCategory.
     *
     * @param mLogCategory the entity to save
     * @return the persisted entity
     */
    @Override
    public MLogCategory save(MLogCategory mLogCategory) {
        log.debug("Request to save MLogCategory : {}", mLogCategory);
        return mLogCategoryRepository.save(mLogCategory);
    }

    /**
     * Get all the mLogCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MLogCategory> findAll(Pageable pageable) {
        log.debug("Request to get all MLogCategories");
        return mLogCategoryRepository.findAll(pageable);
    }


    /**
     * Get one mLogCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MLogCategory> findOne(Long id) {
        log.debug("Request to get MLogCategory : {}", id);
        return mLogCategoryRepository.findById(id);
    }

    /**
     * Delete the mLogCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MLogCategory : {}", id);
        mLogCategoryRepository.deleteById(id);
    }
}

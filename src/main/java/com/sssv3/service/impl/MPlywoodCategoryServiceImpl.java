package com.sssv3.service.impl;

import com.sssv3.service.MPlywoodCategoryService;
import com.sssv3.domain.MPlywoodCategory;
import com.sssv3.repository.MPlywoodCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MPlywoodCategory.
 */
@Service
@Transactional
public class MPlywoodCategoryServiceImpl implements MPlywoodCategoryService {

    private final Logger log = LoggerFactory.getLogger(MPlywoodCategoryServiceImpl.class);

    private final MPlywoodCategoryRepository mPlywoodCategoryRepository;

    public MPlywoodCategoryServiceImpl(MPlywoodCategoryRepository mPlywoodCategoryRepository) {
        this.mPlywoodCategoryRepository = mPlywoodCategoryRepository;
    }

    /**
     * Save a mPlywoodCategory.
     *
     * @param mPlywoodCategory the entity to save
     * @return the persisted entity
     */
    @Override
    public MPlywoodCategory save(MPlywoodCategory mPlywoodCategory) {
        log.debug("Request to save MPlywoodCategory : {}", mPlywoodCategory);
        return mPlywoodCategoryRepository.save(mPlywoodCategory);
    }

    /**
     * Get all the mPlywoodCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MPlywoodCategory> findAll(Pageable pageable) {
        log.debug("Request to get all MPlywoodCategories");
        return mPlywoodCategoryRepository.findAll(pageable);
    }


    /**
     * Get one mPlywoodCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MPlywoodCategory> findOne(Long id) {
        log.debug("Request to get MPlywoodCategory : {}", id);
        return mPlywoodCategoryRepository.findById(id);
    }

    /**
     * Delete the mPlywoodCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MPlywoodCategory : {}", id);
        mPlywoodCategoryRepository.deleteById(id);
    }
}

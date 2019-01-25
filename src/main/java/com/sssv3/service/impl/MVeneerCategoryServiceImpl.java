package com.sssv3.service.impl;

import com.sssv3.service.MVeneerCategoryService;
import com.sssv3.domain.MVeneerCategory;
import com.sssv3.repository.MVeneerCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MVeneerCategory.
 */
@Service
@Transactional
public class MVeneerCategoryServiceImpl implements MVeneerCategoryService {

    private final Logger log = LoggerFactory.getLogger(MVeneerCategoryServiceImpl.class);

    private final MVeneerCategoryRepository mVeneerCategoryRepository;

    public MVeneerCategoryServiceImpl(MVeneerCategoryRepository mVeneerCategoryRepository) {
        this.mVeneerCategoryRepository = mVeneerCategoryRepository;
    }

    /**
     * Save a mVeneerCategory.
     *
     * @param mVeneerCategory the entity to save
     * @return the persisted entity
     */
    @Override
    public MVeneerCategory save(MVeneerCategory mVeneerCategory) {
        log.debug("Request to save MVeneerCategory : {}", mVeneerCategory);
        return mVeneerCategoryRepository.save(mVeneerCategory);
    }

    /**
     * Get all the mVeneerCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MVeneerCategory> findAll(Pageable pageable) {
        log.debug("Request to get all MVeneerCategories");
        return mVeneerCategoryRepository.findAll(pageable);
    }


    /**
     * Get one mVeneerCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MVeneerCategory> findOne(Long id) {
        log.debug("Request to get MVeneerCategory : {}", id);
        return mVeneerCategoryRepository.findById(id);
    }

    /**
     * Delete the mVeneerCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MVeneerCategory : {}", id);
        mVeneerCategoryRepository.deleteById(id);
    }
}

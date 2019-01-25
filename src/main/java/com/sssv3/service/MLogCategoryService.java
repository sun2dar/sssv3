package com.sssv3.service;

import com.sssv3.domain.MLogCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MLogCategory.
 */
public interface MLogCategoryService {

    /**
     * Save a mLogCategory.
     *
     * @param mLogCategory the entity to save
     * @return the persisted entity
     */
    MLogCategory save(MLogCategory mLogCategory);

    /**
     * Get all the mLogCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MLogCategory> findAll(Pageable pageable);


    /**
     * Get the "id" mLogCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MLogCategory> findOne(Long id);

    /**
     * Delete the "id" mLogCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

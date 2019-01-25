package com.sssv3.service;

import com.sssv3.domain.MVeneerCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MVeneerCategory.
 */
public interface MVeneerCategoryService {

    /**
     * Save a mVeneerCategory.
     *
     * @param mVeneerCategory the entity to save
     * @return the persisted entity
     */
    MVeneerCategory save(MVeneerCategory mVeneerCategory);

    /**
     * Get all the mVeneerCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MVeneerCategory> findAll(Pageable pageable);


    /**
     * Get the "id" mVeneerCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MVeneerCategory> findOne(Long id);

    /**
     * Delete the "id" mVeneerCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

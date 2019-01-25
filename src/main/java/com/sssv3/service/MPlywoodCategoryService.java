package com.sssv3.service;

import com.sssv3.domain.MPlywoodCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MPlywoodCategory.
 */
public interface MPlywoodCategoryService {

    /**
     * Save a mPlywoodCategory.
     *
     * @param mPlywoodCategory the entity to save
     * @return the persisted entity
     */
    MPlywoodCategory save(MPlywoodCategory mPlywoodCategory);

    /**
     * Get all the mPlywoodCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MPlywoodCategory> findAll(Pageable pageable);


    /**
     * Get the "id" mPlywoodCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MPlywoodCategory> findOne(Long id);

    /**
     * Delete the "id" mPlywoodCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

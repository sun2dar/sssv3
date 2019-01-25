package com.sssv3.service;

import com.sssv3.domain.MMaterial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MMaterial.
 */
public interface MMaterialService {

    /**
     * Save a mMaterial.
     *
     * @param mMaterial the entity to save
     * @return the persisted entity
     */
    MMaterial save(MMaterial mMaterial);

    /**
     * Get all the mMaterials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MMaterial> findAll(Pageable pageable);


    /**
     * Get the "id" mMaterial.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MMaterial> findOne(Long id);

    /**
     * Delete the "id" mMaterial.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

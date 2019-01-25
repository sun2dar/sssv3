package com.sssv3.service;

import com.sssv3.domain.TMaterial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TMaterial.
 */
public interface TMaterialService {

    /**
     * Save a tMaterial.
     *
     * @param tMaterial the entity to save
     * @return the persisted entity
     */
    TMaterial save(TMaterial tMaterial);

    /**
     * Get all the tMaterials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TMaterial> findAll(Pageable pageable);


    /**
     * Get the "id" tMaterial.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TMaterial> findOne(Long id);

    /**
     * Delete the "id" tMaterial.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

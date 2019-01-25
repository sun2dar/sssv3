package com.sssv3.service;

import com.sssv3.domain.MConstant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MConstant.
 */
public interface MConstantService {

    /**
     * Save a mConstant.
     *
     * @param mConstant the entity to save
     * @return the persisted entity
     */
    MConstant save(MConstant mConstant);

    /**
     * Get all the mConstants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MConstant> findAll(Pageable pageable);


    /**
     * Get the "id" mConstant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MConstant> findOne(Long id);

    /**
     * Delete the "id" mConstant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

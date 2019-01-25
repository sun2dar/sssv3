package com.sssv3.service;

import com.sssv3.domain.MMaterialType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MMaterialType.
 */
public interface MMaterialTypeService {

    /**
     * Save a mMaterialType.
     *
     * @param mMaterialType the entity to save
     * @return the persisted entity
     */
    MMaterialType save(MMaterialType mMaterialType);

    /**
     * Get all the mMaterialTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MMaterialType> findAll(Pageable pageable);


    /**
     * Get the "id" mMaterialType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MMaterialType> findOne(Long id);

    /**
     * Delete the "id" mMaterialType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

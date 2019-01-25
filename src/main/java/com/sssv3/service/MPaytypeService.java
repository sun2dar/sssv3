package com.sssv3.service;

import com.sssv3.domain.MPaytype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MPaytype.
 */
public interface MPaytypeService {

    /**
     * Save a mPaytype.
     *
     * @param mPaytype the entity to save
     * @return the persisted entity
     */
    MPaytype save(MPaytype mPaytype);

    /**
     * Get all the mPaytypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MPaytype> findAll(Pageable pageable);


    /**
     * Get the "id" mPaytype.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MPaytype> findOne(Long id);

    /**
     * Delete the "id" mPaytype.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

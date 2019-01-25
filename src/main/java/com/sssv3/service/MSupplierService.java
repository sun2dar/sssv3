package com.sssv3.service;

import com.sssv3.domain.MSupplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MSupplier.
 */
public interface MSupplierService {

    /**
     * Save a mSupplier.
     *
     * @param mSupplier the entity to save
     * @return the persisted entity
     */
    MSupplier save(MSupplier mSupplier);

    /**
     * Get all the mSuppliers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MSupplier> findAll(Pageable pageable);


    /**
     * Get the "id" mSupplier.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MSupplier> findOne(Long id);

    /**
     * Delete the "id" mSupplier.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

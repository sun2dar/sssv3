package com.sssv3.service;

import com.sssv3.domain.MShift;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MShift.
 */
public interface MShiftService {

    /**
     * Save a mShift.
     *
     * @param mShift the entity to save
     * @return the persisted entity
     */
    MShift save(MShift mShift);

    /**
     * Get all the mShifts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MShift> findAll(Pageable pageable);


    /**
     * Get the "id" mShift.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MShift> findOne(Long id);

    /**
     * Delete the "id" mShift.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

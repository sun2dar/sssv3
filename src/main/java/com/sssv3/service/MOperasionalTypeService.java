package com.sssv3.service;

import com.sssv3.domain.MOperasionalType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MOperasionalType.
 */
public interface MOperasionalTypeService {

    /**
     * Save a mOperasionalType.
     *
     * @param mOperasionalType the entity to save
     * @return the persisted entity
     */
    MOperasionalType save(MOperasionalType mOperasionalType);

    /**
     * Get all the mOperasionalTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MOperasionalType> findAll(Pageable pageable);


    /**
     * Get the "id" mOperasionalType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MOperasionalType> findOne(Long id);

    /**
     * Delete the "id" mOperasionalType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

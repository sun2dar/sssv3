package com.sssv3.service;

import com.sssv3.domain.MMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MMessage.
 */
public interface MMessageService {

    /**
     * Save a mMessage.
     *
     * @param mMessage the entity to save
     * @return the persisted entity
     */
    MMessage save(MMessage mMessage);

    /**
     * Get all the mMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MMessage> findAll(Pageable pageable);


    /**
     * Get the "id" mMessage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MMessage> findOne(Long id);

    /**
     * Delete the "id" mMessage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

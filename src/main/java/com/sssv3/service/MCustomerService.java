package com.sssv3.service;

import com.sssv3.domain.MCustomer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MCustomer.
 */
public interface MCustomerService {

    /**
     * Save a mCustomer.
     *
     * @param mCustomer the entity to save
     * @return the persisted entity
     */
    MCustomer save(MCustomer mCustomer);

    /**
     * Get all the mCustomers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MCustomer> findAll(Pageable pageable);


    /**
     * Get the "id" mCustomer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MCustomer> findOne(Long id);

    /**
     * Delete the "id" mCustomer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

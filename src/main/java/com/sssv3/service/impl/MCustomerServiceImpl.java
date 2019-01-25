package com.sssv3.service.impl;

import com.sssv3.service.MCustomerService;
import com.sssv3.domain.MCustomer;
import com.sssv3.repository.MCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MCustomer.
 */
@Service
@Transactional
public class MCustomerServiceImpl implements MCustomerService {

    private final Logger log = LoggerFactory.getLogger(MCustomerServiceImpl.class);

    private final MCustomerRepository mCustomerRepository;

    public MCustomerServiceImpl(MCustomerRepository mCustomerRepository) {
        this.mCustomerRepository = mCustomerRepository;
    }

    /**
     * Save a mCustomer.
     *
     * @param mCustomer the entity to save
     * @return the persisted entity
     */
    @Override
    public MCustomer save(MCustomer mCustomer) {
        log.debug("Request to save MCustomer : {}", mCustomer);
        return mCustomerRepository.save(mCustomer);
    }

    /**
     * Get all the mCustomers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MCustomer> findAll(Pageable pageable) {
        log.debug("Request to get all MCustomers");
        return mCustomerRepository.findAll(pageable);
    }


    /**
     * Get one mCustomer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MCustomer> findOne(Long id) {
        log.debug("Request to get MCustomer : {}", id);
        return mCustomerRepository.findById(id);
    }

    /**
     * Delete the mCustomer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MCustomer : {}", id);
        mCustomerRepository.deleteById(id);
    }
}

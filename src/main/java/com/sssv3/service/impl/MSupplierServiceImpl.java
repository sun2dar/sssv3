package com.sssv3.service.impl;

import com.sssv3.service.MSupplierService;
import com.sssv3.domain.MSupplier;
import com.sssv3.repository.MSupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MSupplier.
 */
@Service
@Transactional
public class MSupplierServiceImpl implements MSupplierService {

    private final Logger log = LoggerFactory.getLogger(MSupplierServiceImpl.class);

    private final MSupplierRepository mSupplierRepository;

    public MSupplierServiceImpl(MSupplierRepository mSupplierRepository) {
        this.mSupplierRepository = mSupplierRepository;
    }

    /**
     * Save a mSupplier.
     *
     * @param mSupplier the entity to save
     * @return the persisted entity
     */
    @Override
    public MSupplier save(MSupplier mSupplier) {
        log.debug("Request to save MSupplier : {}", mSupplier);
        return mSupplierRepository.save(mSupplier);
    }

    /**
     * Get all the mSuppliers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MSupplier> findAll(Pageable pageable) {
        log.debug("Request to get all MSuppliers");
        return mSupplierRepository.findAll(pageable);
    }


    /**
     * Get one mSupplier by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MSupplier> findOne(Long id) {
        log.debug("Request to get MSupplier : {}", id);
        return mSupplierRepository.findById(id);
    }

    /**
     * Delete the mSupplier by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MSupplier : {}", id);
        mSupplierRepository.deleteById(id);
    }
}

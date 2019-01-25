package com.sssv3.service.impl;

import com.sssv3.service.MPaytypeService;
import com.sssv3.domain.MPaytype;
import com.sssv3.repository.MPaytypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MPaytype.
 */
@Service
@Transactional
public class MPaytypeServiceImpl implements MPaytypeService {

    private final Logger log = LoggerFactory.getLogger(MPaytypeServiceImpl.class);

    private final MPaytypeRepository mPaytypeRepository;

    public MPaytypeServiceImpl(MPaytypeRepository mPaytypeRepository) {
        this.mPaytypeRepository = mPaytypeRepository;
    }

    /**
     * Save a mPaytype.
     *
     * @param mPaytype the entity to save
     * @return the persisted entity
     */
    @Override
    public MPaytype save(MPaytype mPaytype) {
        log.debug("Request to save MPaytype : {}", mPaytype);
        return mPaytypeRepository.save(mPaytype);
    }

    /**
     * Get all the mPaytypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MPaytype> findAll(Pageable pageable) {
        log.debug("Request to get all MPaytypes");
        return mPaytypeRepository.findAll(pageable);
    }


    /**
     * Get one mPaytype by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MPaytype> findOne(Long id) {
        log.debug("Request to get MPaytype : {}", id);
        return mPaytypeRepository.findById(id);
    }

    /**
     * Delete the mPaytype by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MPaytype : {}", id);
        mPaytypeRepository.deleteById(id);
    }
}

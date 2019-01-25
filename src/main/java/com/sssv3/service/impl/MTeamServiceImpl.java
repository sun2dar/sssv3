package com.sssv3.service.impl;

import com.sssv3.service.MTeamService;
import com.sssv3.domain.MTeam;
import com.sssv3.repository.MTeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MTeam.
 */
@Service
@Transactional
public class MTeamServiceImpl implements MTeamService {

    private final Logger log = LoggerFactory.getLogger(MTeamServiceImpl.class);

    private final MTeamRepository mTeamRepository;

    public MTeamServiceImpl(MTeamRepository mTeamRepository) {
        this.mTeamRepository = mTeamRepository;
    }

    /**
     * Save a mTeam.
     *
     * @param mTeam the entity to save
     * @return the persisted entity
     */
    @Override
    public MTeam save(MTeam mTeam) {
        log.debug("Request to save MTeam : {}", mTeam);
        return mTeamRepository.save(mTeam);
    }

    /**
     * Get all the mTeams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MTeam> findAll(Pageable pageable) {
        log.debug("Request to get all MTeams");
        return mTeamRepository.findAll(pageable);
    }


    /**
     * Get one mTeam by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MTeam> findOne(Long id) {
        log.debug("Request to get MTeam : {}", id);
        return mTeamRepository.findById(id);
    }

    /**
     * Delete the mTeam by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MTeam : {}", id);
        mTeamRepository.deleteById(id);
    }
}

package com.sssv3.repository;

import com.sssv3.domain.MTeam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MTeamRepository extends JpaRepository<MTeam, Long> {

    @Query("select m_team from MTeam m_team where m_team.createdby.login = ?#{principal.username}")
    List<MTeam> findByCreatedbyIsCurrentUser();

}

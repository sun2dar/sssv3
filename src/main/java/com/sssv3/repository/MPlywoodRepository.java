package com.sssv3.repository;

import com.sssv3.domain.MPlywood;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MPlywood entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPlywoodRepository extends JpaRepository<MPlywood, Long> {

    @Query("select m_plywood from MPlywood m_plywood where m_plywood.createdby.login = ?#{principal.username}")
    List<MPlywood> findByCreatedbyIsCurrentUser();

}

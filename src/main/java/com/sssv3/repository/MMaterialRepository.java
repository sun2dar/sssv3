package com.sssv3.repository;

import com.sssv3.domain.MMaterial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MMaterialRepository extends JpaRepository<MMaterial, Long> {

    @Query("select m_material from MMaterial m_material where m_material.createdby.login = ?#{principal.username}")
    List<MMaterial> findByCreatedbyIsCurrentUser();

}

package com.sssv3.repository;

import com.sssv3.domain.MVeneer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MVeneer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVeneerRepository extends JpaRepository<MVeneer, Long> {

    @Query("select m_veneer from MVeneer m_veneer where m_veneer.createdby.login = ?#{principal.username}")
    List<MVeneer> findByCreatedbyIsCurrentUser();

}

package com.sssv3.repository;

import com.sssv3.domain.MLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MLogRepository extends JpaRepository<MLog, Long> {

    @Query("select m_log from MLog m_log where m_log.createdby.login = ?#{principal.username}")
    List<MLog> findByCreatedbyIsCurrentUser();

    Page<MLog> findByNamaContainingIgnoreCase(@Param("id") String nama, Pageable var);
}

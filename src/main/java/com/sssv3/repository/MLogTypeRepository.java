package com.sssv3.repository;

import com.sssv3.domain.MLogType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MLogType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MLogTypeRepository extends JpaRepository<MLogType, Long> {

    @Query("select m_log_type from MLogType m_log_type where m_log_type.createdby.login = ?#{principal.username}")
    List<MLogType> findByCreatedbyIsCurrentUser();

}

package com.sssv3.repository;

import com.sssv3.domain.TLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TLogRepository extends JpaRepository<TLog, Long> {

}

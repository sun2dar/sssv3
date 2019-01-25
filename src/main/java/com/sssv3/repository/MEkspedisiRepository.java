package com.sssv3.repository;

import com.sssv3.domain.MEkspedisi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MEkspedisi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MEkspedisiRepository extends JpaRepository<MEkspedisi, Long> {

    @Query("select m_ekspedisi from MEkspedisi m_ekspedisi where m_ekspedisi.createdby.login = ?#{principal.username}")
    List<MEkspedisi> findByCreatedbyIsCurrentUser();

}

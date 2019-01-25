package com.sssv3.repository;

import com.sssv3.domain.MOperasionalType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MOperasionalType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MOperasionalTypeRepository extends JpaRepository<MOperasionalType, Long> {

    @Query("select m_operasional_type from MOperasionalType m_operasional_type where m_operasional_type.createdby.login = ?#{principal.username}")
    List<MOperasionalType> findByCreatedbyIsCurrentUser();

}

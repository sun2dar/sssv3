package com.sssv3.repository;

import com.sssv3.domain.MMaterialType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MMaterialType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MMaterialTypeRepository extends JpaRepository<MMaterialType, Long> {

    @Query("select m_material_type from MMaterialType m_material_type where m_material_type.createdby.login = ?#{principal.username}")
    List<MMaterialType> findByCreatedbyIsCurrentUser();

}

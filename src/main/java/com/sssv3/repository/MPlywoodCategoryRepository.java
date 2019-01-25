package com.sssv3.repository;

import com.sssv3.domain.MPlywoodCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MPlywoodCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPlywoodCategoryRepository extends JpaRepository<MPlywoodCategory, Long> {

    @Query("select m_plywood_category from MPlywoodCategory m_plywood_category where m_plywood_category.createdby.login = ?#{principal.username}")
    List<MPlywoodCategory> findByCreatedbyIsCurrentUser();

}

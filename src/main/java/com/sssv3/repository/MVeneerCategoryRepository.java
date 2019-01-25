package com.sssv3.repository;

import com.sssv3.domain.MVeneerCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MVeneerCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVeneerCategoryRepository extends JpaRepository<MVeneerCategory, Long> {

    @Query("select m_veneer_category from MVeneerCategory m_veneer_category where m_veneer_category.createdby.login = ?#{principal.username}")
    List<MVeneerCategory> findByCreatedbyIsCurrentUser();

}

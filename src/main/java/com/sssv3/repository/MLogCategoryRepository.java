package com.sssv3.repository;

import com.sssv3.domain.MLogCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MLogCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MLogCategoryRepository extends JpaRepository<MLogCategory, Long> {

    @Query("select m_log_category from MLogCategory m_log_category where m_log_category.createdby.login = ?#{principal.username}")
    List<MLogCategory> findByCreatedbyIsCurrentUser();

}

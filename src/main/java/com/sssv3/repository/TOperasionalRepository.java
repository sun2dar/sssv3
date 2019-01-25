package com.sssv3.repository;

import com.sssv3.domain.TOperasional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the TOperasional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TOperasionalRepository extends JpaRepository<TOperasional, Long> {

    @Query("select t_operasional from TOperasional t_operasional where t_operasional.createdby.login = ?#{principal.username}")
    List<TOperasional> findByCreatedbyIsCurrentUser();

}

package com.sssv3.repository;

import com.sssv3.domain.MPlywoodGrade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MPlywoodGrade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPlywoodGradeRepository extends JpaRepository<MPlywoodGrade, Long> {

    @Query("select m_plywood_grade from MPlywoodGrade m_plywood_grade where m_plywood_grade.createdby.login = ?#{principal.username}")
    List<MPlywoodGrade> findByCreatedbyIsCurrentUser();

}

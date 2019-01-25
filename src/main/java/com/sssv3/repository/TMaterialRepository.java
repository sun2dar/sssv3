package com.sssv3.repository;

import com.sssv3.domain.TMaterial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TMaterialRepository extends JpaRepository<TMaterial, Long> {

}

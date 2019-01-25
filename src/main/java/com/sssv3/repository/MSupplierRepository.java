package com.sssv3.repository;

import com.sssv3.domain.MSupplier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MSupplier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MSupplierRepository extends JpaRepository<MSupplier, Long> {

    @Query("select m_supplier from MSupplier m_supplier where m_supplier.createdby.login = ?#{principal.username}")
    List<MSupplier> findByCreatedbyIsCurrentUser();

}

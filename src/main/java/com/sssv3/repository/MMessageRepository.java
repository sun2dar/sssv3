package com.sssv3.repository;

import com.sssv3.domain.MMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MMessageRepository extends JpaRepository<MMessage, Long> {

    @Query("select m_message from MMessage m_message where m_message.createdby.login = ?#{principal.username}")
    List<MMessage> findByCreatedbyIsCurrentUser();

    @Query("select m_message from MMessage m_message where m_message.modifiedby.login = ?#{principal.username}")
    List<MMessage> findByModifiedbyIsCurrentUser();

}

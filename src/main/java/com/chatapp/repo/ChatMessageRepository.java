package com.chatapp.repo;

import com.chatapp.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findBySenderAndReceiverOrReceiverAndSender(
            String sender1, String receiver1,
            String sender2, String receiver2
    );

    List<ChatMessageEntity> findBySenderAndReceiverOrSenderAndReceiverOrderByTimestampAsc(
            String sender1, String receiver1,
            String sender2, String receiver2
    );
}

package com.chatapp.service;

import com.chatapp.dto.ChatMessage;
import com.chatapp.entity.ChatMessageEntity;
import com.chatapp.repo.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public void saveMessage(ChatMessage messageDTO) {
        ChatMessageEntity entity = new ChatMessageEntity();
        entity.setSender(messageDTO.getSender());
        entity.setReceiver(messageDTO.getReceiver());
        entity.setMessage(messageDTO.getMessage());
        entity.setTimestamp(LocalDateTime.now());

        chatMessageRepository.save(entity);
    }

    public List<ChatMessageEntity> getChatHistory(String user1, String user2){
        return chatMessageRepository.findBySenderAndReceiverOrReceiverAndSender(
                user1, user2,
                user2, user1
        );
    }

    public List<ChatMessageEntity> getChatBetweenUsers(String sender, String receiver) {
        return chatMessageRepository
                .findBySenderAndReceiverOrSenderAndReceiverOrderByTimestampAsc(
                        sender, receiver,
                        receiver, sender
                );
    }
}

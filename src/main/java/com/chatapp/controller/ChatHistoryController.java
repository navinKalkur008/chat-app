package com.chatapp.controller;

import com.chatapp.entity.ChatMessageEntity;
import com.chatapp.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatHistoryController {

    private final ChatService chatService;

    public ChatHistoryController(ChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping("/all")
    public List<ChatMessageEntity> getAllChatHistory(
            @RequestParam String user1,
            @RequestParam String user2) {

        return chatService.getChatHistory(user1, user2);
    }

    @GetMapping("/history")
    public List<ChatMessageEntity> getChatHistory(
        @RequestParam String user1,
        @RequestParam String user2) {

        return chatService.getChatBetweenUsers(user1, user2);
    }
}

package com.chatapp.controller;


import com.chatapp.dto.ChatMessage;
import com.chatapp.service.MessageProducer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatWebSocketController {

    private final MessageProducer producer;

    public ChatWebSocketController(MessageProducer producer) {
        this.producer = producer;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage message, Principal principal){

        //Take Sender from JWT Not from UI
        message.setSender(principal.getName());

        System.out.println("✅ Sender from JWT: " + principal.getName());

        producer.sendMessage(message);
    }
}

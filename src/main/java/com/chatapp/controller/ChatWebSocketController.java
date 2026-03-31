package com.chatapp.controller;


import com.chatapp.dto.ChatMessage;
import com.chatapp.service.MessageProducer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatWebSocketController {

    private final MessageProducer producer;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatWebSocketController(MessageProducer producer, SimpMessagingTemplate messagingTemplate) {
        this.producer = producer;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage message, Principal principal){

//        //Take Sender from JWT Not from UI
//        message.setSender(principal.getName());
//
//        System.out.println("✅ Sender from JWT: " + principal.getName());
//
//        producer.sendMessage(message);

        // ✅ Always take sender from JWT (secure)
        String sender = principal.getName();
        message.setSender(sender);

        System.out.println("✅ Sender from JWT: " + sender);

        // ✅ Send to RabbitMQ (for persistence)
        producer.sendMessage(message);

        // ✅ Send to RECEIVER
        messagingTemplate.convertAndSendToUser(
                message.getReceiver(),
                "/queue/messages",
                message
        );

        // ✅ Send back to SENDER (so you see your own message instantly)
        messagingTemplate.convertAndSendToUser(
                sender,
                "/queue/messages",
                message
        );
    }
}

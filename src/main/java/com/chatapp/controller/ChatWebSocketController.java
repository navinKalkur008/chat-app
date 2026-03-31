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

    public ChatWebSocketController(MessageProducer producer) {
        this.producer = producer;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage message, Principal principal){

        // ✅ Always take sender from JWT (secure)
        String sender = principal.getName();
        message.setSender(sender);

        System.out.println("✅ Sender from JWT: " + sender);

        // ✅ Send to RabbitMQ (for persistence)
        producer.sendMessage(message);

//        // ✅ Send to RECEIVER
//        messagingTemplate.convertAndSendToUser(
//                message.getReceiver(),
//                "/queue/messages",
//                message
//        );
//
//        // ✅ Send back to SENDER (so you see your own message instantly)
//        messagingTemplate.convertAndSendToUser(
//                sender,
//                "/queue/messages",
//                message
//        );
    }
}

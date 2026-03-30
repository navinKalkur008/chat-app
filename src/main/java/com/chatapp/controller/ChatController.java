package com.chatapp.controller;

import com.chatapp.dto.ChatMessage;
import com.chatapp.service.MessageProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final MessageProducer producer;


    public ChatController(MessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody ChatMessage message) {
        producer.sendMessage(message);
        return "Message sent successfully!!";
    }
}

package com.chatapp.consumer;

import com.chatapp.config.RabbitMQConfig;
import com.chatapp.dto.ChatMessage;
import com.chatapp.service.ChatService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatService chatService;

    public MessageConsumer(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }


    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveMessage(ChatMessage message) {

        System.out.println("Message Received: "+message.getMessage());

        //SAVE TO DB
        chatService.saveMessage(message);

        //Send to all connected user
       // messagingTemplate.convertAndSend("/topic/messages", message);

        //Send Msg to RECEIVER
        messagingTemplate.convertAndSendToUser(
                message.getReceiver(), // username
                "/queue/messages",    // destination
                message
        );

        //Also show msg in SENDER
        messagingTemplate.convertAndSendToUser(
                message.getSender(),
                "/queue/messages",
                message
        );

    }
}

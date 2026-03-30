package com.chatapp.config;

import com.chatapp.security.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtil jwtUtil;

    public WebSocketConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); //Where Messages are SENT
        config.setApplicationDestinationPrefixes("/app"); // Where Messages come FROM
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
//        registry.addEndpoint("/ws") //endpoint
//                .setHandshakeHandler(new UserHandshakeHandler(jwtUtil))
//                .setAllowedOriginPatterns("*")
//                .withSockJS(); //fallback support
        registry.addEndpoint("/ws")
                .addInterceptors(new JwtHandshakeInterceptor())
                .setHandshakeHandler(new UserHandshakeHandler(jwtUtil))
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}

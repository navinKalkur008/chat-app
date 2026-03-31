package com.chatapp.config;

import com.chatapp.security.JwtUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

    private final JwtUtil jwtUtil;

    public UserHandshakeHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

//        URI uri = request.getURI();
//        String query = uri.getQuery(); // token = qertd......yiu0huy
//
//        String username = "anonymous";
//
//        try {
//            if (query != null && query.startsWith("token=")) {
//
//                String token = query.substring(6); // remove "token="
//
//                // 🔥 Extract username from JWT
//                username = jwtUtil.extractUsername(token);
//
//                System.out.println("✅ WebSocket Authenticated User: " + username);
//            }
//
//        } catch (Exception e) {
//            System.out.println("❌ Invalid WebSocket Token");
//        }
//
//        final String finalUsername = username;
//
//        return () -> finalUsername;

        String token = (String) attributes.get("token");

        if (token != null) {
            String username = jwtUtil.extractUsername(token);

            System.out.println("🔥 WebSocket User: " + username);

            return () -> username;
        }

        return null;
    }
}

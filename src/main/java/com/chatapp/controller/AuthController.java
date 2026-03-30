package com.chatapp.controller;

import com.chatapp.entity.User;
import com.chatapp.repo.UserRepository;
import com.chatapp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){

        System.out.println("Encoded 123 => " + passwordEncoder.encode("123"));
        System.out.println("Matches test => " +
                passwordEncoder.matches("123",
                        "$2a$10$Dow1zJrj1hQn2Q8hX8QF.eXz6ZcZ9Wl7hJk7z9Kq3z8X9YyZzZzZz"));

        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("Entered Password: " + user.getPassword());
        System.out.println("DB Password: " + dbUser.getPassword());

        // ✅ Correct validation
        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}

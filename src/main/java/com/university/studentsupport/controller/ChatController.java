package com.university.studentsupport.controller;

import com.university.studentsupport.service.ChatService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Map<String, Object> chat(
            @RequestBody Map<String, String> request) {

        String message = request.get("message");

        return chatService.processMessage(message);
    }
}
package com.ash.traveally.api.controller;

import com.ash.traveally.api.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("chats")
    public ResponseEntity<Set<Long>> getAllChats() {
        return ResponseEntity.ok(chatService.getAllChats());
    }

    @GetMapping("chats/search")
    public ResponseEntity<Set<Long>> searchUser(@RequestParam(name = "query") String query) {
        return ResponseEntity.ok(chatService.searchUser(query));
    }

    @PostMapping("chats/{userId}")
    public ResponseEntity<String> addChat(@PathVariable Long userId) {
        return ResponseEntity.ok(chatService.addChat(userId));
    }

    @DeleteMapping("chats/{userId}")
    public ResponseEntity<String> deleteChat(@PathVariable Long userId) {
        return ResponseEntity.ok(chatService.deleteChat(userId));
    }
}

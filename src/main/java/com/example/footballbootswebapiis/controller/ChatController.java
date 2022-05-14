package com.example.footballbootswebapiis.controller;

import com.example.footballbootswebapiis.dto.ChatMessageDto;
import com.example.footballbootswebapiis.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<List<ChatMessageDto>> getChatMessages() {
        log.info("Get chat messages request received.");
        return new ResponseEntity(chatService.getChatMessages(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addChatMessage(@RequestBody final ChatMessageDto chatMessageDto) {
        log.info("Add chat message request received.");
        chatService.addChatMessage(chatMessageDto);
        return new ResponseEntity(HttpStatus.OK);
    }

}

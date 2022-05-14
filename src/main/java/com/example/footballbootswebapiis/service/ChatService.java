package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.dto.ChatMessageDto;
import com.example.footballbootswebapiis.model.ChatMessage;
import com.example.footballbootswebapiis.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatService(final ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<ChatMessageDto> getChatMessages() {
        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
        List<ChatMessage> chatMessages = chatMessageRepository.findAll();

        for (ChatMessage chatMessage : chatMessages) {
            chatMessageDtos.add(new ChatMessageDto(chatMessage.getEmail(), chatMessage.getText()));
        }

        return chatMessageDtos;
    }

    public void addChatMessage(final ChatMessageDto chatMessageDto) {
        chatMessageRepository.save(new ChatMessage(chatMessageDto.getUsername(), chatMessageDto.getMessage()));
    }
}

package com.example.footballbootswebapiis.repository;

import com.example.footballbootswebapiis.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
}

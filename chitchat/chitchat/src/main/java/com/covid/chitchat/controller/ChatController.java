package com.covid.chitchat.controller;

import com.covid.chitchat.model.request.ChatRequestDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/listen")
    public String updateChat(ChatRequestDTO requestDTO) throws Exception {
        Thread.sleep(1000);
        return requestDTO.getUsername() + ": " + requestDTO.getChatContent();
    }
}

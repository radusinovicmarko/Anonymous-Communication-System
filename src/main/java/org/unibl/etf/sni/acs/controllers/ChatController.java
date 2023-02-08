package org.unibl.etf.sni.acs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.unibl.etf.sni.acs.models.dto.MessageRequestDTO;
import org.unibl.etf.sni.acs.models.dto.UserDTO;
import org.unibl.etf.sni.acs.services.ChatService;

@Controller
public class ChatController {
    private final SimpMessagingTemplate template;
    private final ChatService chatService;
    @Autowired
    public ChatController(SimpMessagingTemplate template, ChatService chatService) {
        this.template = template;
        this.chatService = chatService;
    }

    @MessageMapping("/message")
    @SendToUser(destinations = "/queue/messages", broadcast = false)
    public void processMessageRequest(String messageRequest) {
        chatService.send(messageRequest);
    }
}

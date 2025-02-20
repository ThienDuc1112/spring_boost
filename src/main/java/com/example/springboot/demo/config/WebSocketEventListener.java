package com.example.springboot.demo.config;

import com.example.springboot.demo.DTOs.Common.ChatMessage;
import com.example.springboot.demo.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemplate;


    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userName = (String)headerAccessor.getSessionAttributes().get("userName");
        if(userName != null){
            log.info("user disconnected {}", userName);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(userName)
                    .build();
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}

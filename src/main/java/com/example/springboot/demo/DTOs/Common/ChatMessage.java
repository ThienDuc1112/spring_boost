package com.example.springboot.demo.DTOs.Common;

import com.example.springboot.demo.enums.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    public String content;
    public String sender;
    public MessageType type;
}

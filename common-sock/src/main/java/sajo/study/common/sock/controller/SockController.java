package sajo.study.common.sock.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import sajo.study.common.core.dto.ChatRoomDTO;
import sajo.study.common.core.dto.MessageDTO;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SockController {
    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/join")
    public void join(ChatRoomDTO room) {
        log.info("room {}", room.getName());
        template.convertAndSend("/topic/chat/" + room.getName(), room);
    }

    @MessageMapping("/chat/{name}/message")
    @SendTo("/topic/chat/{name}/message")
    public MessageDTO message(MessageDTO message,@DestinationVariable String name){
        return message;
    }
}

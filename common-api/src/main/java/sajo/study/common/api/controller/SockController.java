package sajo.study.common.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import sajo.study.common.core.dto.ChatRoomDTO;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SockController {
    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/join")
    public void join(ChatRoomDTO room) {
        log.info("room {}", room.getName());
        template.convertAndSend("/topic/room/" + room.getName(), room);
    }
}

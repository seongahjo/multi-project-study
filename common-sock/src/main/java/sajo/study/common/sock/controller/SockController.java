package sajo.study.common.sock.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import sajo.study.common.core.dto.ChatRoomDTO;
import sajo.study.common.core.dto.MessageDTO;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SockController {

	@MessageMapping("/chat/{name}/join")
	@SendTo("/chat/{name}/join")
	public ChatRoomDTO join(ChatRoomDTO room, @DestinationVariable String name) {
		log.info("JOIN ROOM {}", room.getName());
		return room;
	}

	@MessageMapping("/chat/{name}/message")
	@SendTo("/topic/chat/{name}/message")
	public MessageDTO message(MessageDTO message, @DestinationVariable String name) {
		log.info("MESSAGE {} ROOM {}", message.getMessage(), message.getFrom());
		return message;
	}
}

package sajo.study.common.sock.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import sajo.study.common.core.dto.ChatRoomDTO;
import sajo.study.common.core.dto.MessageDTO;
import sajo.study.common.core.dto.UserLogDTO;
import sajo.study.common.core.util.RestTemplateUtils;
import sajo.study.common.core.util.SimpleTemplate;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SockController {
	private final SimpleTemplate simpleTemplate = new SimpleTemplate("http://localhost:8080/api/", new RestTemplateUtils());

	@MessageMapping("/chat/{idx}/join")
	@SendTo("/topic/chat/{idx}/join")
	public String join(@Header("simpSessionId") String user, ChatRoomDTO room, @DestinationVariable Long idx) {
		log.info("JOIN ROOM {}", room.getName());
		return simpleTemplate.post("userlog", new UserLogDTO(room.getIdx(), user), String.class);
	}

	@MessageMapping("/chat/{idx}/message")
	@SendTo("/topic/chat/{idx}/message")
	public MessageDTO message(MessageDTO message, @DestinationVariable Long idx) {
		log.info("MESSAGE {} ROOM {}", message.getMessage(), message.getFrom());
		return message;
	}
}

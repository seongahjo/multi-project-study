package sajo.study.common.sock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sajo.study.common.core.dto.ChatRoomDTO;
import sajo.study.common.core.dto.MessageDTO;
import sajo.study.common.core.util.SimpleTemplate;
import sajo.study.common.core.util.StompTemplateUtils;
import sajo.study.common.sock.configuration.WebSocketConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebSocketConfig.class)
@Slf4j
public class SocketApplicationTests {
	@Value("${local.server.port}")
	private int port;
	private SimpleTemplate template;

	@BeforeEach
	public void 연결() throws InterruptedException, ExecutionException, TimeoutException {
		template = new SimpleTemplate("", new StompTemplateUtils(port));
	}

	@Test
	public void 방에_접속() throws InterruptedException {
		ChatRoomDTO room = new ChatRoomDTO(1L, "NAME");
		template.post("/app/chat/" + room.getIdx() + "/join", room, ChatRoomDTO.class);
		template.get("/topic/chat/" + room.getIdx() + "/join", ChatRoomDTO.class);
	}

	@Test
	public void 메세지_전송() throws InterruptedException {
		ChatRoomDTO room = new ChatRoomDTO(1L, "MESSAGE_SNED");
		MessageDTO message = new MessageDTO("from", "message");
		template.post("/app/chat/" + room.getIdx() + "/message", message, MessageDTO.class);
		MessageDTO received = template.get("/topic/chat/" + room.getIdx() + "/message", MessageDTO.class);
		assertEquals(message, received);
	}
}

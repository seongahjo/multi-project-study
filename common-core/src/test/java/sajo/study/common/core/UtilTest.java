package sajo.study.common.core;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import sajo.study.common.core.dto.ChatRoomDTO;
import sajo.study.common.core.model.ChatRoom;
import sajo.study.common.core.model.UserLog;
import sajo.study.common.core.util.ModelMapperUtils;
import sajo.study.common.core.util.RestTemplateUtils;
import sajo.study.common.core.util.SimpleTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class UtilTest {

	SimpleTemplate template = new SimpleTemplate("http://localhost:8080/api/",new RestTemplateUtils());
	@Test
	void RestTemplate테스트_POST() {
		ChatRoom test = new ChatRoom("test");
		String message = template.post("room", test, String.class);
		log.info(message);
		ChatRoomDTO get = template.get("room/1", ChatRoomDTO.class);
		assertEquals(ModelMapperUtils.map(test, ChatRoomDTO.class).getName(), get.getName());
	}

	@Test
	void modelMapper_챗룸변환테스트() {
		ChatRoom room = new ChatRoom("room");
		ChatRoomDTO dto = ModelMapperUtils.map(room, ChatRoomDTO.class);
		assertEquals(dto, new ChatRoomDTO("room"));
	}

	@Test
	void messageMapping_메세지변환테스트_유저로그() {
		UserLog u = new UserLog("test", new ChatRoom("testRoom"));
		String message = ModelMapperUtils.map(u, String.class);
		assertEquals("test joins testRoom", message);
	}
	@Test
	void messageMapping_메세지변환테스트_채팅방() {
		ChatRoom chatRoom = new ChatRoom("testRoom");
		String message = ModelMapperUtils.map(chatRoom, String.class);
		assertEquals("testRoom is created", message);
	}
}

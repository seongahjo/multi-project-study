package sajo.study.common.core;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import sajo.study.common.core.dto.ChatRoomDTO;
import sajo.study.common.core.dto.MessageDTO;
import sajo.study.common.core.model.ChatRoom;
import sajo.study.common.core.model.UserLog;
import sajo.study.common.core.util.ModelMapperUtils;
import sajo.study.common.core.util.RestTemplateUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {

	@Test
	void RestTemplate테스트_POST() {
		ChatRoom test = new ChatRoom("test");
		ChatRoomDTO dto = RestTemplateUtils.post("http://localhost:8080/api/room", test, ChatRoomDTO.class);
		ChatRoomDTO get = RestTemplateUtils.get("http://localhost:8080/api/room/1", ChatRoomDTO.class);
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
		String message = ModelMapperUtils.map(u, MessageDTO.class);
		assertEquals("test joins testRoom", message);
	}
	@Test
	void messageMapping_메세지변환테스트_채팅방() {
		ChatRoom chatRoom = new ChatRoom("testRoom");
		String message = ModelMapperUtils.map(chatRoom, MessageDTO.class);
		assertEquals("testRoom is created", message);
	}
}

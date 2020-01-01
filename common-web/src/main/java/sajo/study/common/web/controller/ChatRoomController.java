package sajo.study.common.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sajo.study.common.core.dto.ChatRoomDTO;
import sajo.study.common.core.model.ChatRoom;
import sajo.study.common.web.service.ChatRoomService;

@RestController
@RequestMapping("/api/room")
public class ChatRoomController extends BaseApiController<ChatRoom, ChatRoomDTO> {
    public ChatRoomController(ChatRoomService service) {
        super(service);
    }
}

package sajo.study.common.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sajo.study.common.core.dto.ChatRoomDTO;
import sajo.study.common.core.model.ChatRoom;
import sajo.study.common.core.repository.ChatRoomRepository;

@Service
@Slf4j
public class ChatRoomService extends BaseService<ChatRoom, ChatRoomDTO> {

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        super(chatRoomRepository, ChatRoom.class, ChatRoomDTO.class);
    }

}

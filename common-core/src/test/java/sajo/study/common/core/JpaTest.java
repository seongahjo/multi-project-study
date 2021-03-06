package sajo.study.common.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sajo.study.common.core.dto.ChatRoomDTO;
import sajo.study.common.core.model.ChatRoom;
import sajo.study.common.core.model.UserLog;
import sajo.study.common.core.repository.ChatRoomRepository;
import sajo.study.common.core.repository.UserLogRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sajo.study.common.core.util.ModelMapperUtils.map;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaTest {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    @Test
    public void chatRoom_저장후_DTO변환() {
        ChatRoom saved = chatRoomRepository.save(new ChatRoom("test"));
        ChatRoomDTO dto = map(saved, ChatRoomDTO.class);
        assertEquals(saved.getName(), dto.getName());
        ChatRoom reverse = map(dto, ChatRoom.class);
        assertEquals(saved, reverse);
    }

    @Test
    public void userLog_저장() {
        ChatRoom room = new ChatRoom("test");
        chatRoomRepository.save(room);
        UserLog u = new UserLog(room);
        u.setRoom(room);
        UserLog saved = userLogRepository.save(u);
        assertEquals(u, saved);
    }
}

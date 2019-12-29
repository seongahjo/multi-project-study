package sajo.study.common.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sajo.study.common.core.model.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}

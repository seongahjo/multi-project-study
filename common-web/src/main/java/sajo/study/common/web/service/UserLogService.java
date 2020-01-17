package sajo.study.common.web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sajo.study.common.core.dto.UserLogDTO;
import sajo.study.common.core.model.ChatRoom;
import sajo.study.common.core.model.UserLog;
import sajo.study.common.core.repository.ChatRoomRepository;
import sajo.study.common.core.repository.UserLogRepository;
import sajo.study.common.core.util.ModelMapperUtils;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
public class UserLogService extends BaseService<UserLog, UserLogDTO> {
	private final ChatRoomRepository chatRoomRepository;

	public UserLogService(UserLogRepository userLogRepository, ChatRoomRepository chatRoomRepository) {
		super(userLogRepository, UserLog.class, UserLogDTO.class);
		this.chatRoomRepository = chatRoomRepository;
	}

	@Override
	public String save(UserLogDTO dto) {
		Long roomIdx = dto.getChatRoomIdx();
		ChatRoom chatRoom = chatRoomRepository.findById(roomIdx).orElseThrow(EntityNotFoundException::new);
		UserLog userLog = this.repository.save(new UserLog(dto.getUserName(), chatRoom));
		return ModelMapperUtils.map(userLog, String.class);
	}
}

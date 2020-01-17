package sajo.study.common.core.util;

import org.modelmapper.ModelMapper;
import sajo.study.common.core.convert.MessageMapper;
import sajo.study.common.core.model.BaseEntity;
import sajo.study.common.core.model.ChatRoom;
import sajo.study.common.core.model.UserLog;

import java.util.Arrays;

public class ModelMapperUtils {
	private static final ModelMapper modelMapper = new ModelMapper();

	private ModelMapperUtils() {
	}

	public static <T extends BaseEntity> String map(T entity, Class<String> clazz) {
		return MessageMapping.mapping(entity);
	}

	public static <T, U> U map(T entity, Class<U> clazz) {
		return modelMapper.map(entity, clazz);
	}

	public enum MessageMapping {
		CHATROOM(ChatRoom.class, entity -> {
			ChatRoom room = (ChatRoom) entity;
			return String.format("%s is created", room.getName());
		}),
		USERLOG(UserLog.class, entity -> {
			UserLog userlog = (UserLog) entity;
			return String.format("%s joins %s", userlog.getUserName(), userlog.getRoom().getName());
		});


		MessageMapping(Class<?> clazz, MessageMapper messageMapper) {
			this.clazz = clazz;
			this.messageMapper = messageMapper;
		}

		public static String mapping(BaseEntity entity) {
			Class<?> clazz = entity.getClass();
			MessageMapper mapper = Arrays.stream(values())
					.filter(val -> val.clazz == clazz)
					.findAny()
					.map(MessageMapping::getMessageMapper)
					.orElse(et -> "");
			return mapper.map(entity);
		}

		public MessageMapper getMessageMapper() {
			return messageMapper;
		}

		Class<?> clazz;
		MessageMapper messageMapper;
	}
}




package sajo.study.common.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = {"idx", "name"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO extends BaseDTO {
	private Long idx;
	private String name;

	public ChatRoomDTO(String name) {
		super();
		this.name = name;
	}
}

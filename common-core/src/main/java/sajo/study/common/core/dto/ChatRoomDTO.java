package sajo.study.common.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"idx", "name"}, callSuper = false)
public class ChatRoomDTO extends BaseDTO {
    private Long idx;
    private String name;

    public ChatRoomDTO() {
    }

    public ChatRoomDTO(String name) {
        super();
        this.name = name;
    }
}

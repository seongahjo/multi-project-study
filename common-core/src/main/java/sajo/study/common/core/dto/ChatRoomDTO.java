package sajo.study.common.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = {"idx", "name"}, callSuper = false)
@NoArgsConstructor
public class ChatRoomDTO extends BaseDTO {
    private Long idx;
    private String name;

    public ChatRoomDTO(String name) {
        super();
        this.name = name;
    }
}

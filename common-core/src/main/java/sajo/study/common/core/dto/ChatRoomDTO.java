package sajo.study.common.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of={"idx","name"})
public class ChatRoomDTO {
    private Long idx;
    private String name;
    @JsonIgnore
    private LocalDate createdAt;

    public ChatRoomDTO() {
    }

    public ChatRoomDTO(String name) {
        this.name = name;
        this.createdAt = LocalDate.now();
    }
}

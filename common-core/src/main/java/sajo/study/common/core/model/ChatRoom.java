package sajo.study.common.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of={"idx","name"})
public class ChatRoom {
    private Long idx;
    private String name;
    @JsonIgnore
    private LocalDate createdAt;

    public ChatRoom() {
    }

    public ChatRoom(String name) {
        this.name = name;
        this.createdAt = LocalDate.now();
    }
}

package sajo.study.common.core.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChatRoom {
    private Long idx;
    private String name;
    private LocalDate createdAt;

    public ChatRoom(String name) {
        this.name = name;
        this.createdAt = LocalDate.now();
    }
}

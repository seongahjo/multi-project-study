package sajo.study.common.core.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class ChatRoom {
    @Id
    @GeneratedValue
    private Long idx;
    private String name;
    private LocalDate createdAt;

    public ChatRoom(String name) {
        this.name = name;
        this.createdAt = LocalDate.now();
    }
}

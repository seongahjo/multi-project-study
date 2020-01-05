package sajo.study.common.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UserLog extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;

	private String userName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_idx")
	private ChatRoom room;

	public UserLog(ChatRoom room) {
		this.room = room;
	}

	public UserLog(String userName, ChatRoom room) {
		this.userName = userName;
		this.room = room;
	}
}

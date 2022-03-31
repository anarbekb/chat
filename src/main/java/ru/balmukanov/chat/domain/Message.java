package ru.balmukanov.chat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private MessageType type;
	private String content;
	private String sender;
	private Instant createdAt;
}
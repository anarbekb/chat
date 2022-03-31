package ru.balmukanov.chat.adapter.http.chat.dto;

import lombok.Data;
import ru.balmukanov.chat.domain.MessageType;

@Data
public class MessageDto {
	private MessageType type;
	private String content;
	private String sender;
}
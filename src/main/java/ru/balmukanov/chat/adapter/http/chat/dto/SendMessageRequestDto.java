package ru.balmukanov.chat.adapter.http.chat.dto;

import lombok.Data;

@Data
public class SendMessageRequestDto {
	private String type;
	private String content;
	private String sender;
}
package ru.balmukanov.chat.adapter.http.chat.dto;

import lombok.Data;

@Data
public class JoinUserRequestDto {
	private String helloMessage;
	private String username;
}
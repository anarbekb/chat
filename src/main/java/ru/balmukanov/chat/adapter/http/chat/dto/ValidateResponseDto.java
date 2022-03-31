package ru.balmukanov.chat.adapter.http.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateResponseDto {
	private Boolean isValid;
	private String message;
}
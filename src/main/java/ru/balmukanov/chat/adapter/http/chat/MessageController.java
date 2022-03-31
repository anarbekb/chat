package ru.balmukanov.chat.adapter.http.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.balmukanov.chat.adapter.http.chat.dto.*;
import ru.balmukanov.chat.app.api.MessageService;
import ru.balmukanov.chat.domain.Message;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MessageController {
	private final Mapper mapper;
	private final MessageService messageService;

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public MessageDto send(@Payload SendMessageRequestDto request) {
		Message message = messageService.create(mapper.mapToDomain(request));
		return mapper.mapToDto(message);
	}

	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public MessageDto addUser(@Payload JoinUserRequestDto request, SimpMessageHeaderAccessor headerAccessor) {
		Message message = messageService.create(mapper.mapToDomain(request));

		Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSender());
		return mapper.mapToDto(message);
	}
}
package ru.balmukanov.chat.adapter.http.chat.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.balmukanov.chat.adapter.http.chat.Mapper;
import ru.balmukanov.chat.adapter.http.chat.dto.MessageDto;
import ru.balmukanov.chat.app.api.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController("restMessageController")
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
	private final MessageService messageService;
	private final Mapper mapper;

	@GetMapping("/list")
	public List<MessageDto> list() {
		return messageService.today()
			.stream()
			.map(mapper::mapToDto)
			.collect(Collectors.toList());
	}
}
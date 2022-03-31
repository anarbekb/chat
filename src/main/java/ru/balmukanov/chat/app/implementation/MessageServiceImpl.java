package ru.balmukanov.chat.app.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balmukanov.chat.app.api.MessageRepository;
import ru.balmukanov.chat.app.api.MessageService;
import ru.balmukanov.chat.domain.Message;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
	private final MessageRepository messageRepository;

	@Transactional
	public Message create(Message message) {
		return messageRepository.save(message);
	}
}
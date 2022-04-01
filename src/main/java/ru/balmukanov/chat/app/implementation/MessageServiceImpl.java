package ru.balmukanov.chat.app.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balmukanov.chat.app.api.MessageRepository;
import ru.balmukanov.chat.app.api.MessageService;
import ru.balmukanov.chat.domain.Message;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
	private final MessageRepository messageRepository;

	@Transactional
	public Message create(Message message) {
		return messageRepository.save(message);
	}

	public List<Message> today() {
		Calendar day = Calendar.getInstance();
		day.set(Calendar.MILLISECOND, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.HOUR_OF_DAY, 0);
		return messageRepository.betweenDate(Instant.ofEpochMilli(day.getTimeInMillis()) , Instant.now());
	}
}
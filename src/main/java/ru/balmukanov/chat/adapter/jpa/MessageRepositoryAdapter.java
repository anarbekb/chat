package ru.balmukanov.chat.adapter.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.balmukanov.chat.app.api.MessageRepository;
import ru.balmukanov.chat.domain.Message;

import java.time.Instant;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryAdapter implements MessageRepository {
	private final MessageJpaRepository messageJpaRepository;

	@Override
	public Message save(Message message) {
		return messageJpaRepository.save(message);
	}

	public List<Message> betweenDate(Instant begin, Instant end) {
		return messageJpaRepository.getMessagesByCreatedAtBetween(begin, end);
	}
}
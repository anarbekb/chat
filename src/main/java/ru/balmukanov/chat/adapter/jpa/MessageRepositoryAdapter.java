package ru.balmukanov.chat.adapter.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.balmukanov.chat.app.api.MessageRepository;
import ru.balmukanov.chat.domain.Message;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryAdapter implements MessageRepository {
	private final MessageJpaRepository messageJpaRepository;

	@Override
	public Message save(Message message) {
		return messageJpaRepository.save(message);
	}
}
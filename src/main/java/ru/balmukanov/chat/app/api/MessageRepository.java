package ru.balmukanov.chat.app.api;

import ru.balmukanov.chat.domain.Message;

import java.time.Instant;
import java.util.List;

public interface MessageRepository {
	Message save(Message message);
	List<Message> betweenDate(Instant begin, Instant end);
}
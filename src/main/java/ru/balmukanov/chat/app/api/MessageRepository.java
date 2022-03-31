package ru.balmukanov.chat.app.api;

import ru.balmukanov.chat.domain.Message;

public interface MessageRepository {
	Message save(Message message);
}
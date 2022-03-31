package ru.balmukanov.chat.app.api;

import ru.balmukanov.chat.domain.Message;

public interface MessageService {
	public Message create(Message message);
}
package ru.balmukanov.chat.app.api;

import ru.balmukanov.chat.domain.Message;

import java.util.List;

public interface MessageService {
	Message create(Message message);
	List<Message> today();
}
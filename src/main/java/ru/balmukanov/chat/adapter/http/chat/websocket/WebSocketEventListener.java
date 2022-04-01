package ru.balmukanov.chat.adapter.http.chat.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ru.balmukanov.chat.adapter.http.chat.Mapper;
import ru.balmukanov.chat.app.api.MessageService;
import ru.balmukanov.chat.domain.Message;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {
	private final Mapper mapper;
	private final SimpMessageSendingOperations template;
	private final MessageService messageService;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		log.info("Received a new websocket connection");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
		if (username != null) {
			Message message = messageService.create(mapper.mapToDomainLeaveUser(username));
			template.convertAndSend("/topic/public", message);
		}
	}
}
package ru.balmukanov.chat.adapter.http.chat;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.balmukanov.chat.adapter.http.chat.dto.JoinUserRequestDto;
import ru.balmukanov.chat.adapter.http.chat.dto.MessageDto;
import ru.balmukanov.chat.adapter.http.chat.dto.SendMessageRequestDto;
import ru.balmukanov.chat.app.api.exception.AdapterMappingException;
import ru.balmukanov.chat.domain.Message;
import ru.balmukanov.chat.domain.MessageType;

import java.time.Instant;

@Component
public class Mapper {
	private final ModelMapper mapper;
	public Mapper() {
		this.mapper = new ModelMapper();
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		this.mapper.typeMap(SendMessageRequestDto.class, Message.class)
			.setPostConverter(sendMessageRequestDtoToMessageConverter());

		this.mapper.typeMap(JoinUserRequestDto.class, Message.class)
			.addMapping(JoinUserRequestDto::getUsername, Message::setSender)
			.addMapping(JoinUserRequestDto::getHelloMessage, Message::setContent)
			.setPostConverter(joinUserRequestDtoToMessageConverter());
	}

	public Message mapToDomain(SendMessageRequestDto request) {
		try {
			return mapper.map(request, Message.class);
		} catch (Exception e) {
			throw new AdapterMappingException(e);
		}
	}

	public Message mapToDomain(JoinUserRequestDto request) {
		try {
			return mapper.map(request, Message.class);
		} catch (Exception e) {
			throw new AdapterMappingException(e);
		}
	}

	public Message mapToDomainLeaveUser(String username) {
		try {
			Message message = new Message();
			message.setSender(username);
			message.setType(MessageType.LEAVE);
			message.setCreatedAt(Instant.now());

			return message;
		} catch (Exception e) {
			throw new AdapterMappingException(e);
		}
	}

	public MessageDto mapToDto(Message message) {
		try {
			return mapper.map(message, MessageDto.class);
		} catch (Exception e) {
			throw new AdapterMappingException(e);
		}
	}

	private Converter<SendMessageRequestDto, Message> sendMessageRequestDtoToMessageConverter() {
		return context -> {
			SendMessageRequestDto source = context.getSource();
			Message destination = context.getDestination();

			destination.setCreatedAt(Instant.now());
			destination.setType(MessageType.valueOf(source.getType()));

			return destination;
		};
	}

	private Converter<JoinUserRequestDto, Message> joinUserRequestDtoToMessageConverter() {
		return context -> {
			JoinUserRequestDto source = context.getSource();
			Message destination = context.getDestination();

			if (source.getHelloMessage() == null || source.getHelloMessage().isEmpty()) {
				destination.setType(MessageType.JOIN);
			} else {
				destination.setType(MessageType.JOIN_WITH_MESSAGE);
			}

			destination.setCreatedAt(Instant.now());

			return destination;
		};
	}
}
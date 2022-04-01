package ru.balmukanov.chat.adapter.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balmukanov.chat.domain.Message;

import java.time.Instant;
import java.util.List;

public interface MessageJpaRepository extends JpaRepository<Message, Long> {
	List<Message> getMessagesByCreatedAtBetween(Instant begin, Instant end);
}
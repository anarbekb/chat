package ru.balmukanov.chat.adapter.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.balmukanov.chat.domain.Message;

public interface MessageJpaRepository extends JpaRepository<Message, Long> {
}
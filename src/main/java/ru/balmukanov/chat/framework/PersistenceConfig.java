package ru.balmukanov.chat.framework;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ru.balmukanov.chat")
@EntityScan("ru.balmukanov.chat")
public class PersistenceConfig {
}
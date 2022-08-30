package com.example.azure.scsservicebus;

import com.azure.spring.messaging.checkpoint.Checkpointer;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import static com.azure.spring.messaging.AzureHeaders.CHECKPOINTER;

@Configuration
@Slf4j
public class ConsumerService {


    @Bean
    public Consumer<Message<String>> consume() {
        return message -> {
            Checkpointer checkpointer = (Checkpointer) message.getHeaders().get(CHECKPOINTER);
            log.info("New message received: '{}'", message.getPayload());
            checkpointer.success()
                    .doOnSuccess(s -> log.info("Message '{}' successfully checkpointed", message.getPayload()))
                    .doOnError(e -> log.error("Error found", e))
                    .block();
        };
    }

}

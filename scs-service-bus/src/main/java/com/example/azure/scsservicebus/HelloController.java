package com.example.azure.scsservicebus;

import com.azure.spring.messaging.checkpoint.Checkpointer;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.azure.spring.messaging.AzureHeaders.CHECKPOINTER;

@RestController
@Slf4j
public class HelloController {

    private StreamBridge streamBridge;

    public HelloController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping("/hello" )
    public void register(@RequestParam(defaultValue = "test") String param) {
        log.info("/hello called with param=>"+param);
        streamBridge.send("hello-out-0", param);
    }

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

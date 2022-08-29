package com.example.azure.scsservicebus;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    private StreamBridge streamBridge;

    public HelloController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping("/hello" )
    public void register(@RequestParam String param) {
        streamBridge.send("hello-out-0", param);
    }

    @Bean
    public Consumer<String> consume() {
        return value -> System.out.println("Received events from hello: " + value);
    }

}

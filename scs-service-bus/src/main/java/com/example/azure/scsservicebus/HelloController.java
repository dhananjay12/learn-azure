package com.example.azure.scsservicebus;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;

import java.util.function.Consumer;

@RestController
@Slf4j
public class HelloController {

    private final StreamBridge streamBridge;

    public HelloController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping("/hello")
    public void register(@RequestParam(defaultValue = "test") String param) {
        log.info("/hello called with param=>" + param);
        streamBridge.send("hello-out-0", param);
    }

    @GetMapping("/test")
    public void test() {
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i <= 3000; i++) {
            String key = "description" + i;
            String value = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque cursus, nisl sed vestibulum tempus, " +
                    "lectus leo venenatis massa, ut ullamcorper libero enim sit amet mi. Fusce aliquam aliquet augue, " +
                    "vitae pellentesque ex dignissim quis. Sed et neque ligula. Vivamus elementum varius lobortis. Sed " +
                    "efficitur, lorem vel efficitur faucibus, libero ex iaculis nisi, id consectetur massa orci ac tortor. " +
                    "Nullam malesuada lectus enim, vitae bibendum ex pulvinar nec. Nulla facilisi. Suspendisse potenti.";
            jsonObject.put(key, value);
        }

        streamBridge.send("hello-out-0", jsonObject);
    }

    @Bean
    public Consumer<Message<String>> consume() {
        return message -> log.info("New message received: '{}'", message.getPayload());
    }

}

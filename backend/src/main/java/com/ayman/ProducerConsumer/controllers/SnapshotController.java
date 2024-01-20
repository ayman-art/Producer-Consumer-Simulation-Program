package controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;



@Controller
public class SnapshotController {
    @MessageMapping("/greeting")
    public String handle(String greeting) {
        return "hello";
    }
}

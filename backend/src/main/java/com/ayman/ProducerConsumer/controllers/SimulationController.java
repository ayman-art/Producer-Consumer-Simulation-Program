package com.ayman.ProducerConsumer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@Controller
@CrossOrigin("*")
public class SimulationController {

    @MessageMapping("/start")
    public void startSimulation(@Payload Map<String, Object> data) {
    }

    @MessageMapping("/stop")
    public void stopSimulation(@Payload String sim) {
    }

    @MessageMapping("/stop")
    public void replaySimulation(@Payload String sim) {
    }
}

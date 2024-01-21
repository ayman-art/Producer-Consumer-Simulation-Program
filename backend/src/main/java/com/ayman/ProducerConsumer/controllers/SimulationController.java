package com.ayman.ProducerConsumer.controllers;

import com.ayman.ProducerConsumer.Service.SimulationService;
import com.ayman.ProducerConsumer.models.Snapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin("*")
public class SimulationController {

    @Autowired
    SimulationService simulationService;
    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/start")
    @SendTo("/simulate/public")
    public void startSimulation(@Payload Map<String, Object> data) throws InterruptedException {
        simulationService.startSimulation(data);
    }

    @MessageMapping("/stop")
    public void stopSimulation() {
        simulationService.stopSimulation();
    }

    @MessageMapping("/replay")
    public void replaySimulation() {
        simulationService.replaySimulation();
    }
}

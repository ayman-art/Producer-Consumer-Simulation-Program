package com.ayman.ProducerConsumer.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ProductConsumerSimulation {
    private List<Machine> machines;
    private List<QueueManager> queues;
    private List<Snapshot> snapshots;

    @Autowired
    SimpMessagingTemplate template;

    public void start() {
        template.convertAndSend("/simulate/public", "Start");
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Start");
//                template.convertAndSend("/simulate/public", "Start");
//            }
//        };
//
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//        executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public void replay() {
        template.convertAndSend("/simulate/public", "Replay");

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Replay");
//                template.convertAndSend("/simulate/public", "Start");
//            }
//        };
//
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//        executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        template.convertAndSend("/simulate/public", "Stop");

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Stop");
//                template.convertAndSend("/simulate/public", "Start");
//            }
//        };
//
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//        executorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public void setQueues(List<QueueManager> queues) {
        this.queues = queues;
    }
}

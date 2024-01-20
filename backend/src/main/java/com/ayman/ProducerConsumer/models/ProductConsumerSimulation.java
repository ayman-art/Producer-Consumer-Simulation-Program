package com.ayman.ProducerConsumer.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductConsumerSimulation {
    private List<Machine> machines;
    private List<QueueManager> queues;
    private final List<Snapshot> snapshots = new ArrayList<>();
    private InputSeed inputSeed;

    @Autowired
    SimpMessagingTemplate template;

    public void start() {
        inputSeed.setState(true);
        inputSeed.start();

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
        inputSeed.setState(false);
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

    public void takeSnapshot() {
        Snapshot snapshot = new Snapshot();

        addAllMachinesToSnapshot(snapshot);
        addAllQueuesToSnapShot(snapshot);

        this.snapshots.add(snapshot);

        template.convertAndSend("/simulate/public", snapshot);
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public void setQueues(List<QueueManager> queues) {
        this.queues = queues;
    }

    public void setInputQueue(QueueManager inputQueue) {
        inputSeed = new InputSeed(this, inputQueue);
    }

    private void addAllMachinesToSnapshot(Snapshot snapshot) {
        for (Machine machine : machines)
            snapshot.addMachine(machine.getCurrentState());
    }

    private void addAllQueuesToSnapShot(Snapshot snapshot) {
        for (QueueManager manager : queues)
            snapshot.addQueue(manager.getCurrentState());
    }
}

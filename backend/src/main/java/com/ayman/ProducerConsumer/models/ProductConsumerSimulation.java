package com.ayman.ProducerConsumer.models;

import com.ayman.ProducerConsumer.Service.SimulationService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductConsumerSimulation {
    private List<Machine> machines;
    private List<QueueManager> queues;
    private final List<Snapshot> snapshots = new ArrayList<>();
    private InputSeed inputSeed;
    private final SimulationService service;

    public ProductConsumerSimulation(SimulationService service) {
        this.service = service;
    }

    public void start() {
        inputSeed.setState(true);
        inputSeed.start();

//        template.convertAndSend("/simulate/public", "Start");
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
//        template.convertAndSend("/simulate/public", "Replay");

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
//        template.convertAndSend("/simulate/public", "Stop");

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

    public Snapshot takeSnapshot() {
        Snapshot snapshot = new Snapshot();

        addAllMachinesToSnapshot(snapshot);
        addAllQueuesToSnapShot(snapshot);

        this.snapshots.add(snapshot);

//        template.convertAndSend("/simulate/public", snapshot);
        return snapshot;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public void setQueues(List<QueueManager> queues) {
        this.queues = queues;
    }

    public void setInputQueue(QueueManager inputQueue) {
        inputSeed = new InputSeed(service, inputQueue);
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

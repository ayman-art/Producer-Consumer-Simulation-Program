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
    private SimulationState currentState;

    public ProductConsumerSimulation(SimulationService service) {
        this.service = service;
        currentState = SimulationState.STOP;
    }

    public void start() {
        currentState = SimulationState.START;
        inputSeed.setState(currentState);
        inputSeed.start();
    }

    public void replay() {
        currentState = SimulationState.REPLAY;
        inputSeed.setState(currentState);

        Thread thread = new Thread(replayLogic());
        thread.start();
    }

    public void stop() {
        currentState = SimulationState.STOP;
        inputSeed.setState(currentState);
    }

    public Snapshot takeSnapshot() {
        Snapshot snapshot = new Snapshot();

        addAllMachinesToSnapshot(snapshot);
        addAllQueuesToSnapShot(snapshot);

        this.snapshots.add(snapshot);

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

    private Runnable replayLogic() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (currentState == SimulationState.REPLAY && i < snapshots.size()) {
                    Snapshot curSnapshot = snapshots.get(i++);
                    service.sendSnapshot(curSnapshot);
                    try {
                        Thread.sleep(curSnapshot.getTime() * 1000L);
                    } catch (InterruptedException e) {
                        System.out.println("Error in replaying");
                    }
                }
            }
        };

        return runnable;
    }
}

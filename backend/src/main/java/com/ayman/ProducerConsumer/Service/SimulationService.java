package com.ayman.ProducerConsumer.Service;

import com.ayman.ProducerConsumer.models.Machine;
import com.ayman.ProducerConsumer.models.ProductConsumerSimulation;
import com.ayman.ProducerConsumer.models.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimulationService {
    @Autowired
    ProductConsumerSimulation simulation;

    Map<Integer, QueueManager> queueManagerMap = new HashMap<>();
    List<Machine> machines;

    public void startSimulation(Map<String, Object> data) {
        var queues = parsesQueues((ArrayList) data.get("queues"));
        machines = parseMachines((ArrayList) data.get("machines"));

        simulation.setQueues(queues);
        simulation.setMachines(machines);
        simulation.setInputQueue(queueManagerMap.get(0));

        simulation.start();
    }

    public void stopSimulation() {
        simulation.stop();
    }

    public void replaySimulation() {
        simulation.replay();
    }

    private List<Machine> parseMachines(List<Object> data) {
        List<Machine> curMachines = new ArrayList<>();

        for (Object obj : data) {
            Map<String, Object> mp = (Map<String, Object>) obj;
            Integer id = (Integer) mp.get("id");
            Integer inQueueId = (Integer) mp.get("in");
            Integer outQueueId = (Integer) mp.get("out");

            QueueManager inQueue = queueManagerMap.get(inQueueId);
            QueueManager outQueue = queueManagerMap.get(outQueueId);
            Machine machine = new Machine(id, "00CC00", outQueue);
            inQueue.subscribe(machine);

            curMachines.add(machine);
        }

        return curMachines;
    }

    private List<QueueManager> parsesQueues(List<Object> queues) {
        List<QueueManager> managers = new ArrayList<>();

        for (Object queue : queues) {
            Map<String, Integer> mp = (Map<String, Integer>) queue;
            Integer id = mp.get("id");

            var manager = new QueueManager(mp.get("id"), new LinkedList<>(), new ArrayList<>());
            managers.add(manager);
            queueManagerMap.put(id, manager);
        }

        return managers;
    }
}

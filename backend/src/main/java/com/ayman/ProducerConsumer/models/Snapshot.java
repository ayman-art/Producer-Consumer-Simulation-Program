package com.ayman.ProducerConsumer.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Snapshot {
    private int time = 1;
    private List<Map<String, Object>> queues = new ArrayList<>();
    private List<Map<String, Object>> machines = new ArrayList<>();

    public void setTime(int time) {
        this.time = time;
    }

    public void addQueue(Map<String, Object> queue) {
        queues.add(queue);
    }

    public void addMachine(Map<String, Object> machine) {
        machines.add(machine);
    }

    public int getTime() {
        return time;
    }

    public List<Map<String, Object>> getQueues() {
        return queues;
    }

    public List<Map<String, Object>> getMachines() {
        return machines;
    }
}

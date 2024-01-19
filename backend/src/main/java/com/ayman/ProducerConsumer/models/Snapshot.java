package com.ayman.ProducerConsumer.models;

import java.util.List;
import java.util.Map;

public class Snapshot {
    private int time;
    private List<Map<String, Object>> queues;
    private List<Map<String, Object>> machines;

    public void setTime(int time) {
        this.time = time;
    }

    public void addQueue(Map<String, Object> queue) {
        queues.add(queue);
    }

    public void addMachine(Map<String, Object> machine) {
        machines.add(machine);
    }
}

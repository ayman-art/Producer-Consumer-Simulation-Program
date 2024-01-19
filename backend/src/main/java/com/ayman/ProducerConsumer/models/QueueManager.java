package com.ayman.ProducerConsumer.models;

import java.util.Queue;

public record QueueManager(int id, String color, Queue<Machine> listeners) {

    public int getListenersCount() {
        return listeners.size();
    }

    public void subscribe(Machine machine) {
        listeners.add(machine);
    }

    public void unsubscribe(Machine machine) {
        listeners.remove(machine);
    }

    public void notifyAllListeners() {
        for (Machine machine : listeners) {
        }
    }


}

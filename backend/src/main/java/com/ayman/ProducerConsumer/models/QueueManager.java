package com.ayman.ProducerConsumer.models;

import java.util.Queue;

public class QueueManager {

    final int id;
    String color;
    Queue<Machine> listeners;

    public QueueManager(int id, String color, Queue<Machine> listeners) {
        this.id = id;
        this.color = color;
        this.listeners = listeners;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public Queue<Machine> getListeners() {
        return listeners;
    }

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

package com.ayman.ProducerConsumer.models;

import java.util.List;
import java.util.Queue;

public class QueueManager {

    private final Queue<Product> products;
    private final List<Machine> listeners;
    private final long id;

    public QueueManager(int id, Queue<Product> products, List<Machine> listeners) {
        this.id = id;
        this.products = products;
        this.listeners = listeners;
    }


    public long getId() {
        return id;
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
            synchronized (machine) {
                if (machine.isCompleted()) {
                    continue;
                }
                machine.update();
            }
        }
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product getProduct() {
        return products.poll();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }






}

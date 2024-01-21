package com.ayman.ProducerConsumer.models;

import com.ayman.ProducerConsumer.Service.SimulationService;
import org.w3c.dom.ls.LSResourceResolver;

import java.util.*;

public class QueueManager {
    private final int id;
    private final Queue<Product> products;
    private final List<Machine> listeners;
    private final SimulationService service;

    public QueueManager(int id, Queue<Product> products, List<Machine> listeners, SimulationService service) {
        this.id = id;
        this.products = products;
        this.listeners = listeners;
        this.service = service;
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

    public void notifyAllListeners(Product product) {
        addProduct(product);

        for (Machine machine : listeners) {
            if (isEmpty())
                return;

            if (machine.isCompleted()) {
                machine.setCurrentProduct(getProduct());
                try {
                    machine.process();
                } catch (IllegalThreadStateException e) {
                    System.out.println("Er");
                }
            }
        }
        this.service.sendSnapshot();
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

    public Map<String, Object> getCurrentState() {
        Map<String, Object> state = new HashMap<>();
        state.put("id", id);
        state.put("count", products.size());

        return state;
    }
}

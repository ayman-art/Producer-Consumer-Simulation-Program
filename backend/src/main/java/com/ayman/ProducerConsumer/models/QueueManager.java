package com.ayman.ProducerConsumer.models;

import com.ayman.ProducerConsumer.Service.SimulationService;

import java.util.*;

public class QueueManager {
    private final int id;
    private final List<Queue<Product>> products;
    private final List<Machine> listeners;
    private final SimulationService service;

    public QueueManager(int id, SimulationService service) {
        this.id = id;
        this.products = new ArrayList<>();
        this.listeners = new ArrayList<>();
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
        products.add(new LinkedList<>());
    }

    public void unsubscribe(Machine machine) {
        listeners.remove(machine);
    }

    public void notifyAllListeners(Product product) {
        synchronized (this) {
            if (product != null)
                addProduct(product);

            for (int i = 0; i < listeners.size(); ++i) {
                Machine machine = listeners.get(i);
                if (isEmpty(i))
                    continue;

                if (machine.getMachineState() == MachineState.READY) {
                    addProductToMachine(machine, i);
                    products.get(i).poll();
                } else if (machine.getMachineState() == MachineState.WAITING) {
                    if (machine.getCurrentProduct().getId() == getProduct(i).getId()) {
                        addProductToMachine(machine, i);
                        products.get(i).poll();
                    }
                }
            }
            this.service.sendSnapshot();
        }
    }

    public void addProduct(Product product) {
//        products.add(product);
        if (products.isEmpty())
            products.add(new LinkedList<>());

        for (Queue<Product> queue : products) {
            if (!queue.contains(product))
                queue.add(product);
        }
    }

    public Product getProduct(int index) {
        return products.get(index).peek();
    }

    public boolean isEmpty(int index) {
        return products.get(index).isEmpty();
    }

    public Map<String, Object> getCurrentState() {
        Map<String, Object> state = new HashMap<>();
        int productCount = 0;
        for (Queue<Product> queue : products)
            productCount += queue.size();

        state.put("id", id);
        state.put("count",productCount);
        return state;
    }

    private void addProductToMachine(Machine machine, int index) {
        machine.setCurrentProduct(getProduct(index));
        try {
            machine.process();
        } catch (IllegalThreadStateException e) {
            System.out.println("Er");
        }
    }
}

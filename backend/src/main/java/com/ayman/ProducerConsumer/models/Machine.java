package com.ayman.ProducerConsumer.models;

import java.util.*;

public class Machine implements Runnable {
    private final int machineId;
    private final int servingTime;
    private String color;
    private MachineState machineState;
    private Product currentProduct;
    private final QueueManager outQueueManager;
    private final List<QueueManager> inQueueManagers;
    private int parts;
    private final int maxParts;

    public Machine(int id, String color, QueueManager outQueueManager, List<QueueManager> inQueueManagers) {
        this.machineId = id;
        this.color = color;
        this.outQueueManager = outQueueManager;
        this.inQueueManagers = inQueueManagers;
        this.servingTime = new Random().nextInt(500, 5000);
        this.machineState = MachineState.READY;
        this.parts = 0;
        this.maxParts = inQueueManagers.size();
    }

    public int getMachineId() {
        return machineId;
    }

    public String getColor() {
        return color;
    }

    public int getServingTime() {
        return servingTime;
    }

    public MachineState getMachineState() {
        return machineState;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setState(MachineState machineState) {
        this.machineState = machineState;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
        this.color = this.currentProduct.getColor();
        this.parts++;
        setState(MachineState.WAITING);
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Map<String, Object> getCurrentState() {
        Map<String, Object> state = new HashMap<>();
        state.put("id", machineId);
        state.put("color", color);

        return state;
    }

    public void process() {
        if (machineCompleted()) {
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                setState(MachineState.BUSY);
                Thread.sleep(servingTime);
//                outQueueManager.notifyAllListeners(currentProduct);
                notifyOutListeners();
                resetMachine();
                notifyInListeners();
            } catch (InterruptedException e) {
                System.out.println("Error occurred in Processing Machine");
            }
        }
    }

    private void resetMachine() {
//        System.out.println("Finish Thread machine id: " + machineId + " prod id: " + currentProduct.getId());
        this.color = "00CC00";
        this.machineState = MachineState.READY;
        this.currentProduct = null;
        this.parts = 0;
    }

    private void notifyInListeners() {
        for (QueueManager manager : inQueueManagers)
            manager.notifyAllListeners(null);
    }

    private void notifyOutListeners() {
        outQueueManager.notifyAllListeners(currentProduct);
    }

    private boolean machineCompleted() {
        if (getMachineId() == 2) {
            System.out.println("parts: " + this.parts + " maxParts: " + maxParts);
        }
        return true;
    }
}

package com.ayman.ProducerConsumer.models;

import java.util.*;

public class Machine implements Runnable {
    private final int machineId;
    private final int servingTime;
    private String color;
    private boolean completed;
    private Product currentProduct;
    private final QueueManager outQueueManager;
    private final List<QueueManager> inQueueManagers;

    public Machine(int id, String color, QueueManager outQueueManager, List<QueueManager> inQueueManagers) {
        this.machineId = id;
        this.color = color;
        this.outQueueManager = outQueueManager;
        this.inQueueManagers = inQueueManagers;
        this.servingTime = new Random().nextInt(500, 5000);
        this.completed = true;
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

    public boolean isCompleted() {
        return completed;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setState(boolean completed) {
        this.completed = completed;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
        this.color = this.currentProduct.getColor();
        setState(false);
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
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(servingTime);
//                outQueueManager.notifyAllListeners(currentProduct);
            notifyOutListeners();
            resetMachine();
            notifyInListeners();
        } catch (InterruptedException e) {
            System.out.println("Error occurred in Processing Machine");
        }
    }

    private void resetMachine() {
        System.out.println("Finish Thread machine id: " + machineId + " prod id: " + currentProduct.getId());
        this.color = "00CC00";
        this.completed = true;
        this.currentProduct = null;
    }

    private void notifyInListeners() {
        for (QueueManager manager : inQueueManagers)
            manager.notifyAllListeners(null);
    }

    private void notifyOutListeners() {
        outQueueManager.notifyAllListeners(currentProduct);
    }
}

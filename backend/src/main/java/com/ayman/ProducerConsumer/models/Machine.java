package com.ayman.ProducerConsumer.models;

import com.ayman.ProducerConsumer.Service.SimulationService;

import java.util.*;

public class Machine implements Runnable {
    private final int machineId;
    private final int servingTime;
    private String color;
    private boolean completed;
    private Product currentProduct;
    private final QueueManager queueManager;
    private List<QueueManager> inputQueues = new ArrayList<>();
    private Thread runningThread;

    private SimulationService simulationService;

    public Machine(int id, String color, QueueManager queueManager, SimulationService simulationService) {
        this.machineId = id;
        this.color = color;
        this.queueManager = queueManager;
        this.servingTime = new Random().nextInt(500, 5000);
        this.completed = true;
        this.simulationService = simulationService;
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

    public void subscribe(QueueManager queueManager) {
        inputQueues.add(queueManager);
    }

    public void unsubscribe(QueueManager queueManager) {
        inputQueues.remove(queueManager);
    }

    public void notifyAllListeners() {

        for(QueueManager queueManager: inputQueues) {
            synchronized (queueManager) {
                if (queueManager.isEmpty())
                    return;

                if (this.isCompleted()) {
                    this.setCurrentProduct(queueManager.getProduct());

                }
            }

            if(!this.isCompleted())
                this.process();
        }

        simulationService.sendSnapshot();

    }

    public void process() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        synchronized (queueManager) {
            try {
                Thread.sleep(servingTime);
                queueManager.notifyAllListeners(currentProduct);
                resetMachine();
            } catch (InterruptedException e) {
                System.out.println("Error occurred in Processing Machine");
            }
        }

        notifyAllListeners();
    }
//    @Override
//    public void run() {
//        try {
//            Thread.sleep(servingTime);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        queueManager.notifyAllListeners(currentProduct);
//        resetMachine();
//    }

    private void resetMachine() {
        System.out.println("Finish Thread machine id: " + machineId + " prod id: " + currentProduct.getId());
        this.color = "00CC00";
        this.completed = true;
        this.currentProduct = null;
    }
}

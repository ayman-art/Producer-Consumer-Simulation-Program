package com.ayman.ProducerConsumer.models;

import java.util.ArrayList;
import java.util.Random;

public class Machine {


    private final int id;
    private final int servingTime;
    private String color;


    private boolean completed;

    private Product currentProduct;

    private final QueueManager queueManager;
    private final ArrayList<QueueManager> inputQueueManagers;

    private Thread runningThread;

    public Machine(int id, String color, QueueManager queueManager, ArrayList<QueueManager> inputQueueManagers) {
        this.id = id;
        this.color = color;
        this.queueManager = queueManager;
        this.inputQueueManagers = inputQueueManagers;
        this.servingTime = new Random().nextInt(5000);
    }

    public int getId() {
        return id;
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

    public QueueManager getQueueManager() {
        return queueManager;
    }

    public ArrayList<QueueManager> getInputQueueManagers() {
        return inputQueueManagers;
    }

    public void setState(boolean completed) {
        this.completed = completed;
    }


    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void notifyQueueManagers() {
        for (QueueManager queueManager : inputQueueManagers) {
            queueManager.notifyAllListeners();
        }
    }

    public void update() {
        if (runningThread != null && runningThread.isAlive()) {
            return;
        }

        runningThread = new Thread(() -> {
            try {
                for (QueueManager queueManager : inputQueueManagers) {
                    if (!queueManager.isEmpty()) {
                        Product product = queueManager.getProduct();
                        setColor(product.getColor());
                        setCurrentProduct(product);
                        completed = false;
                        Thread.sleep(servingTime);
                        completed = true;
                        notifyQueueManagers();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}


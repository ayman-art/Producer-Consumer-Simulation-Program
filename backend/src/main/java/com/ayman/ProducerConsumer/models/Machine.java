package com.ayman.ProducerConsumer.models;

import java.util.Random;

public class Machine {



    private final int id;
    private final int servingTime;
    private String color;


    private boolean completed;

    private Product currentProduct;

    private final QueueManager queueManager;

    public Machine(int id, String color, QueueManager queueManager) {
        this.id = id;
        this.color = color;
        this.queueManager = queueManager;
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

    public void setState(boolean completed) {
        this.completed = completed;
    }



    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

package com.ayman.ProducerConsumer.models;

import java.util.Random;

public class Machine {

    static final String defaultColor = "ffffff";

    static int idCounter = 0;

    private final int id;
    private String color;

    private final int servingTime;

    private boolean completed;

    private Product currentProduct;

    private final QueueManager queueManager;

    public Machine(QueueManager queueManager) {
        this.id = idCounter++;
        this.color = defaultColor;
        this.servingTime = new Random().nextInt(5000);
        this.queueManager = queueManager;
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

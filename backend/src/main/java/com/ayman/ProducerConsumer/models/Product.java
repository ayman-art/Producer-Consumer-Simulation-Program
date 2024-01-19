package com.ayman.ProducerConsumer.models;

import java.awt.*;
import java.util.Random;

public class Product {
    private final long id;
    final String color;

    public Product(long id) {
        this.id = id;
        //generating random color
        this.color = generateRandomColor();
    }

    private String generateRandomColor() {
        int numColor = new Random().nextInt(0x1000000);
        return String.format("%06x", numColor);
    }

    public long getId() {
        return id;
    }

    public String getColor() {
        return this.color;
    }
}

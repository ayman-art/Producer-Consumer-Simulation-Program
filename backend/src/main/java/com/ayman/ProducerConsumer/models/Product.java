package com.ayman.ProducerConsumer.models;

import java.awt.*;

public class Product {

    private final long id;
    final String color;

    public Product(long id) {
        this.id = id;
        //generating random color
        this.color = generateRandomColor();
    }

    private String generateRandomColor() {
        Color color = new Color((int) (Math.random() * 0x1000000));
        return String.format("%06x", color.getRGB() & 0xFFFFFF);
    }

    public long getId() {
        return id;
    }

    public String getColor() {
        return this.color;
    }
}

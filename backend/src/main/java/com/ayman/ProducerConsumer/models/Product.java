package com.ayman.ProducerConsumer.models;

import java.awt.*;

public class Product {
    static long idCounter = 0;

    private long id;
    String color;

    public Product() {
        this.id = idCounter++;
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

    public void setColor(String color) {
        this.color = color;
    }
}

package com.ayman.simulation.models;

import java.awt.*;

public class Product {
    static long idCounter = 0;

    private long id;
    private Color color;

    public Product() {
        this.id = idCounter++;
        //generating random color
        this.color = new Color((int) (Math.random() * Color.white.getRGB()));



    }
    public long getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }
}

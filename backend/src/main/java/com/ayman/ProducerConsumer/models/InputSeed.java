package com.ayman.ProducerConsumer.models;

import java.util.concurrent.ThreadLocalRandom;

public class InputSeed extends Thread{
    private boolean state;
    private final QueueManager queueManager;
    private final ProductConsumerSimulation simulation;

    public InputSeed(ProductConsumerSimulation simulation, QueueManager queueManager) {
        this.simulation = simulation;
        this.queueManager = queueManager;
    }

    @Override
    public void run() {
        synchronized (queueManager) {
            while (state) {
                int inputRate = ThreadLocalRandom.current().nextInt(200, 3000);
                try {
                    Product product = new Product();
                    queueManager.notifyAllListeners(product);
                    this.simulation.takeSnapshot();
                    Thread.sleep(inputRate);

                    System.out.println(product.getId());
                } catch (InterruptedException e) {
                    System.out.println("Error occurred in Input Seed");
                }
            }
        }
    }

    public void setState(boolean state) {
        this.state = state;
    }
}

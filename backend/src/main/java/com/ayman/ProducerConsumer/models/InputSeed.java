package com.ayman.ProducerConsumer.models;

import com.ayman.ProducerConsumer.Service.SimulationService;

import java.util.concurrent.ThreadLocalRandom;

public class InputSeed extends Thread{
    private boolean state;
    private final QueueManager queueManager;

    private final SimulationService service;

    public InputSeed(SimulationService service, QueueManager queueManager) {
        this.service = service;
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
                    this.service.sendSnapshot();
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

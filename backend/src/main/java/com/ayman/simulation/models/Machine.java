package com.ayman.simulation.models;

import java.util.Random;
import java.util.random.RandomGenerator;

public class Machine {

    

    static long machineIdCounter = 0;
    static long maxRunningTime = RandomGenerator.getDefault().nextLong(5000); // 5 seconds
    private long machineId;
    private long runningTime;
    private Thread runningThread;

    public Machine() {
        this.machineId = machineIdCounter++;
    }

    public long getMachineId() {
        return machineId;
    }

    public long getRunningTIme() {
        return runningTime;
    }


}


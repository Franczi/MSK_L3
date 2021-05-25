package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private static final double INFECTION_RATION = 2;
    private static final int NEWBORNS_COUNT = 4;
    private static final int SIM_STEP = 1; // 1 week
    private static int populationSize = 1000;

    private static int weeks = 1;
    private static int infectedAmount;
    private static int hiddenAmount;
    private static int susceptibleAmount;
    private static int immuneAmount;


    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("measles-sim.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        initialization();
        while (susceptibleAmount >= 1) {
            simulationStep(bw);
            weeks += SIM_STEP;
        }
        bw.close();
    }

    public static void initialization() {
        infectedAmount = 2;
        hiddenAmount = 5;
        susceptibleAmount = populationSize - infectedAmount + hiddenAmount;
    }

    public static void simulationStep(BufferedWriter bw) throws IOException {
        int infectedPreviousWeek = infectedAmount;
        infectedAmount = hiddenAmount;
        hiddenAmount = (int) ((double) infectedPreviousWeek * INFECTION_RATION);
        if (hiddenAmount > susceptibleAmount) {
            hiddenAmount = susceptibleAmount;
        }
        populationSize = populationSize + NEWBORNS_COUNT;
        susceptibleAmount = Math.max(0, populationSize - (immuneAmount + hiddenAmount + infectedAmount));
        immuneAmount += infectedPreviousWeek;
        bw.write(infectedAmount + " " + susceptibleAmount + " " + hiddenAmount + " " + immuneAmount);
        bw.newLine();
    }

}

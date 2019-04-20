package Main;


import java.awt.image.BufferedImage;
import java.util.List;

import Main.Fitness;
import GA.Individual;

public class FitnessInParallel implements Runnable{

    private int start;
    private int end;
    private boolean done = false;
    private List<Individual> poolOfSounds;

    private Individual original;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (poolOfSounds == null) {
            return;
        }

        for (int i = start; i < end; i++) {
            if (poolOfSounds.get(i).getFitness() == null) {
                Fitness.computeFitnessIndividual( original,poolOfSounds.get(i));
            }
        }
        done = true;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<Individual> getPoolOfSounds() {
        return poolOfSounds;
    }

    public void setPoolOfSounds(List<Individual> poolOfSounds) {
        this.poolOfSounds = poolOfSounds;
    }

    public Individual getOriginal() {
        return original;
    }

    public void setOriginal(Individual original) {
        this.original = original;
    }
}
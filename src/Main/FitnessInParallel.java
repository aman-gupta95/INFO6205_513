package Main;


import java.awt.image.BufferedImage;
import java.util.List;

import Main.Fitness;
import GA.Individual;

public class FitnessInParallel implements Runnable{

    private int start;//starting point of individual id for a particular thread
    private int end;////ending point of individual id for a particular thread
    private boolean done = false;//status if work is done or not
    private List<Individual> poolOfSounds;//pool of sounds of a particular thread

    private Individual original;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (poolOfSounds == null) {
            return;
        }
        //when a thread comes here based on its respective individuals it performs fitness
        //threads help to process different individuals parallel by dividing work
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
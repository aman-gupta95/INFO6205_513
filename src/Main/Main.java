package Main;

import GA.*;
import ProcessSound.CreateMidi;
import ProcessSound.SeeMidi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final int POOL_SIZE = 1000;
    private static final int MAX_VELOCITY = 127;
    private static final int MAX_KEY = 127;
    private static final int MAX_GENERATIONS = 10000;
    private static final int BEST_COUNT = 10;
    private static final int MUTATION_COUNT = 1;
    private static int GOAL_LENGTH=0 ;
    private static int TICK_LENGTH;
    private static ArrayList<Individual> poolOfSounds;
    private static ArrayList<Genotype> originalSound;
    private static List<Long> ticks;

    public static void main(String args[]) throws Exception {
        SeeMidi midi = new SeeMidi();
        originalSound = midi.parseMidi();
        GOAL_LENGTH=originalSound.size();
        int POP_SIZE = midi.getTrackSize();
        TICK_LENGTH = midi.getTickLength();
        long MAX_TICK = midi.getTick();
        ticks = midi.getTicks();
        poolOfSounds = Populate.initPool(POOL_SIZE, originalSound.size(), MAX_KEY, MAX_VELOCITY, MAX_TICK);
        System.out.println(GOAL_LENGTH);

        double maxFitness=0;
        Fitness fit = new Fitness();
        Individual original = new Individual(originalSound);
        createMidi(original, "original");

        for(int i=0; i<MAX_GENERATIONS && maxFitness<0.99*7*GOAL_LENGTH; i++){
            for(int j=0; j<poolOfSounds.size(); j++) {
                fit.computeFitnessIndividual(original, poolOfSounds.get(j));
            }
                Collections.sort(poolOfSounds,Collections.reverseOrder());

                System.out.println("Generation: "+ (i+1) + " has best individual with Fitness: " + poolOfSounds.get(0).getFitness());

                if(i%100==0){
                    createMidi(poolOfSounds.get(0), i);
                }
                poolOfSounds = CrossOver.mateBest(poolOfSounds, BEST_COUNT,GOAL_LENGTH,MUTATION_COUNT, MAX_KEY, MAX_VELOCITY,original);
                maxFitness = poolOfSounds.get(0).getFitness();

            }
            createMidi(poolOfSounds.get(0),"final");
        }


    private static void createMidi(Individual bestSound, int generation){
        CreateMidi.generateMidi(ticks, bestSound, TICK_LENGTH, generation);
    }

    private static void createMidi(Individual bestSound, String type){
//        CreateMidi.generateMidi(ticks, bestSound, TICK_LENGTH, type);
    }

}

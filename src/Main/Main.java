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
    private static final int THREADS = 4;
    private static List<FitnessInParallel> parallelFitnessEvaluators;

    public static void main(String args[]) throws Exception {
        SeeMidi midi = new SeeMidi();
        originalSound = midi.parseMidi("audio.mid");
        GOAL_LENGTH=originalSound.size();
        int POP_SIZE = midi.getTrackSize();
        TICK_LENGTH = midi.getTickLength();
        long MAX_TICK = midi.getTick();
        ticks = midi.getTicks();
        poolOfSounds = Populate.initPool(POOL_SIZE, originalSound.size(), MAX_KEY, MAX_VELOCITY, MAX_TICK);
        //initThreads();
        System.out.println(GOAL_LENGTH);

        double maxFitness=0;
        Fitness fit = new Fitness();
        Individual original = new Individual(originalSound);
        createMidi(original, "original");

        long time;
        long startTime = System.currentTimeMillis();
        for(int i=0; i<MAX_GENERATIONS && maxFitness<0.99*7*GOAL_LENGTH; i++){

           // fitnessOfMultipleMidis(original,poolOfSounds);


//            startTime = System.currentTimeMillis();
            for(int j=0; j<POOL_SIZE; j++) {
                fit.computeFitnessIndividual(original, poolOfSounds.get(j));
            }
//           endTime = System.currentTimeMillis();
//            time = (endTime - startTime);
//            System.out.println("without parallel processing "+ time +"ms" );
//            System.out.println("i am here");

                Collections.sort(poolOfSounds,Collections.reverseOrder());

                System.out.println("Generation: "+ (i+1) + " has best individual with Fitness: " + poolOfSounds.get(0).getFitness());

                if(i%100==0){
                    createMidi(poolOfSounds.get(0), i);
                }
                poolOfSounds = CrossOver.mateBest(poolOfSounds, BEST_COUNT,GOAL_LENGTH,MUTATION_COUNT, MAX_KEY, MAX_VELOCITY,original);
                maxFitness = poolOfSounds.get(0).getFitness();

            }
        long endTime = System.currentTimeMillis();
        time = (endTime - startTime);
        System.out.println("without parallel processing "+ time +"ms" );
            createMidi(poolOfSounds.get(0),"final");

        }


    private static void createMidi(Individual bestSound, int generation){
        CreateMidi.generateMidi(ticks, bestSound, TICK_LENGTH, generation);
    }

    private static void createMidi(Individual bestSound, String type){
        CreateMidi.generateMidi(ticks, bestSound, TICK_LENGTH, type);
    }

    private static void fitnessOfMultipleMidis(Individual original,List<Individual> poolOfSounds ) {
        // split between threads
        int forOneThread = poolOfSounds.size() /THREADS;
        for (int i = 0; i < THREADS; i++) {
            FitnessInParallel evaluator = parallelFitnessEvaluators.get(i);
            evaluator.setStart(i * forOneThread);
            evaluator.setEnd((i + 1) * forOneThread + 1);
            evaluator.setPoolOfSounds(poolOfSounds);
            evaluator.setOriginal(original);
            if (i + 1 == THREADS) {
                evaluator.setEnd(poolOfSounds.size());
            }
            new Thread(evaluator).start();
        }

//         see if all done
        while (!parallelFitnessEvaluatorDone()) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static boolean parallelFitnessEvaluatorDone() {
        for (FitnessInParallel evaluator : parallelFitnessEvaluators) {
            if (!evaluator.isDone()) {
                return false;
            }
        }
        resetparallelFitnessEvaluator();
        return true;
    }

    private static void resetparallelFitnessEvaluator() {
        for (FitnessInParallel evaluator : parallelFitnessEvaluators) {
            evaluator.setDone(false);
        }
    }

    private static void initThreads() {
        parallelFitnessEvaluators = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            FitnessInParallel evaluator = new FitnessInParallel();
            evaluator.setPoolOfSounds(poolOfSounds);
            evaluator.setOriginal(new Individual(originalSound));
            parallelFitnessEvaluators.add(evaluator);
        }
    }

}

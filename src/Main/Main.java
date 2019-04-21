package Main;

import GA.*;
import ProcessSound.CreateMidi;
import ProcessSound.SeeMidi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final int POOL_SIZE = 1000;//pool size
    //gene property ranges
    private static final int MAX_VELOCITY = 127;//for Velocity
    private static final int MAX_KEY = 127;//for Key

    private static final int MAX_GENERATIONS = 10000;//maximum number of generations till best gene is produced

    private static final int BEST_COUNT = 10;//count of best individuals promoted to next generation through selection and help in generating nect generation

    private static final int MUTATION_COUNT = 1;//number of times mutation to be done for a specific generation

    private static int GOAL_LENGTH=0 ;//length of goal

    private static int TICK_LENGTH;
    private static ArrayList<Individual> poolOfSounds;//Individual population in each generation
    private static ArrayList<Genotype> originalSound;//Goal Individual
    private static List<Long> ticks;//track which helps to generate back the midi by processing individual similar to time
    private static Writer writer;
    private static final int THREADS = 4;//number of threads
    private static List<FitnessInParallel> parallelFitnessEvaluators;//helps in parallel processing

    public static void main(String args[]) throws Exception {
        SeeMidi midi = new SeeMidi();
        originalSound = midi.parseMidi("audio.mid");//generate individual after parsing input file
        GOAL_LENGTH=originalSound.size();
        int POP_SIZE = midi.getTrackSize();
        TICK_LENGTH = midi.getTickLength();
        long MAX_TICK = midi.getTick();
        ticks = midi.getTicks();

        //function to generate initial population
        poolOfSounds = Populate.initPool(POOL_SIZE, originalSound.size(), MAX_KEY, MAX_VELOCITY, MAX_TICK);

        //start parallel processing
        initThreads();
        System.out.println(GOAL_LENGTH);
        try{//make a record of csv file
            File file = new File("FitnessSheet.csv");
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            writer = new FileWriter(file);
            writer.append("Generation,Fitness");
            writer.append("\r\n");
        }catch(IOException e){
            e.printStackTrace();
        }
        double maxFitness=0;
        Fitness fit = new Fitness();
        Individual original = new Individual(originalSound);//create individual for original set of genes
        createMidi(original, "original");

        long time;
        long startTime = System.currentTimeMillis();

        //function for fitness computation of each generation where loop is to iterate for each generation
        for(int i=0; i<MAX_GENERATIONS && maxFitness<0.99*7*GOAL_LENGTH; i++){
            //maxFitness<0.99*7*GOAL_LENGTH --> 7 is fitness of each individual,
            // Goal length is length of individual. So, we are measuring if the fitness of best candidate of a particular generation is approximately equal to maximum fitness.
            // If yes the best individual is found or else goes to next genration.
            //Another breaking poing is count of generations

            //compute fitness of pool with the help of parallel processing
            fitnessOfMultipleMidis(original,poolOfSounds);

                //sort the pool to get individual fitness in descending order
                Collections.sort(poolOfSounds,Collections.reverseOrder());

                System.out.println("Generation: "+ (i+1) + " has best individual with Fitness: " + poolOfSounds.get(0).getFitness());

                if(i%100==0){
                    createMidi(poolOfSounds.get(0), i);//create sound after 100 generations to keep record in Saved sounds through out the process
                }

                //make a call to cross-over where internally it takes care of selecting first best individuals and add to next generation pool
                poolOfSounds = CrossOver.mateBest(poolOfSounds, BEST_COUNT,GOAL_LENGTH,MUTATION_COUNT, MAX_KEY, MAX_VELOCITY,original);
                maxFitness = poolOfSounds.get(0).getFitness();//help in keep track of each generation best individual fitness. When ever it reaches the best fitness which represent it is similar to goal then the loop stops
                generateExcel(i+1, maxFitness);
            }
        writer.close();
        long endTime = System.currentTimeMillis();
        time = (endTime - startTime);//just keep track of time taken for entire process using parallel processing
        System.out.println("with parallel processing "+ time +"ms" );
            createMidi(poolOfSounds.get(0),"final");//help to create final file in both Saved sound and Original sound


        }

        private static void generateExcel(int generationNumber, double fitness) throws IOException{
            writer.append(String.valueOf(generationNumber)+","+String.valueOf(fitness));
            writer.append("\r\n");
        }

    //record midi for 100 generations
    private static void createMidi(Individual bestSound, int generation){
        CreateMidi.generateMidi(ticks, bestSound, TICK_LENGTH, generation);
    }
    //record midi for original and final individuals
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

        //see if all done
        while (!parallelFitnessEvaluatorDone()) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    //check status of each evaluator if done or not
    private static boolean parallelFitnessEvaluatorDone() {
        for (FitnessInParallel evaluator : parallelFitnessEvaluators) {
            if (!evaluator.isDone()) {
                return false;
            }
        }
        resetparallelFitnessEvaluator();
        return true;
    }

    //if done set evaluator to done state
    private static void resetparallelFitnessEvaluator() {
        for (FitnessInParallel evaluator : parallelFitnessEvaluators) {
            evaluator.setDone(false);
        }
    }
    //help to start the processing for threads
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

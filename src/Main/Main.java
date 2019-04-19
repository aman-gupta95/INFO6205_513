package Main;

import GA.*;
import ProcessSound.CreateMidi;
import ProcessSound.SeeMidi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final int POOL_SIZE = 10000;
    private static final int MAX_VELOCITY = 127;
    private static final int MAX_KEY = 127;
    private static final int MAX_GENERATIONS = 1000;
    private static final int BEST_COUNT = 5000;
    private static int TICK_LENGTH;
    private static ArrayList<Individual> poolOfSounds;
    private static ArrayList<Genotype> originalSound;
    private static List<Long> ticks;

    public static void main(String args[]) throws Exception {
        SeeMidi midi = new SeeMidi();
        originalSound = midi.parseMidi();
        int POP_SIZE = midi.getTrackSize();
        TICK_LENGTH = midi.getTickLength();
        long MAX_TICK = midi.getTick();
        ticks = midi.getTicks();
        poolOfSounds = Populate.initPool(POOL_SIZE, POP_SIZE, MAX_KEY, MAX_VELOCITY, MAX_TICK);
        System.out.println(originalSound.size());
        double maxFitness=Double.MAX_VALUE;
        Fitness fit = new Fitness();
        Individual original = new Individual(originalSound);
        for(int i=0;i<10;i++) {
            System.out.println(original.getIndividual().get(i));
        }
        for(int i=0; i<MAX_GENERATIONS && maxFitness>1; i++){
            for(int j=0; j<poolOfSounds.size(); j++) {
                fit.computeFitnessIndividual(original, poolOfSounds.get(j));
            }
                Collections.sort(poolOfSounds);

                System.out.println(poolOfSounds.get(0).getFitness());
//                double fitness = 100.00 - (100.00*128/(poolOfSounds.get(0).getFitness()*355));
                System.out.println("Generation: "+ (i+1) + " has best individual with Fitness: " + poolOfSounds.get(0).getFitness());
                for(int k=0;k<10;k++) {
                    System.out.println(poolOfSounds.get(k).getIndividual().get(0));
                }
                //if(i%99==0){
                    createMidi(poolOfSounds.get(0), i);
               // }
                poolOfSounds = CrossOver.mateBest(poolOfSounds, BEST_COUNT,ticks.size(), 1, MAX_KEY, MAX_VELOCITY);
                maxFitness = poolOfSounds.get(0).getFitness();
            }
        }


    private static void createMidi(Individual bestSound, int generation){
        CreateMidi.generateMidi(ticks, bestSound, TICK_LENGTH, generation);
    }
//
//    public Individual generateIndividual(){
//        ArrayList<Genotype> genes = new ArrayList<>();
//        Individual individual = new Individual(genes);
//        for(int i= 0;i<200;i++){
//            int tick = getRandomTick(0,1100);
//            boolean note = getRandomNote();
//            int key = getRandomKey(1,100);
//            int velocity = getRandomVelocity(10,100);
//            Genotype gene = new Genotype();
//            gene.setTick(tick);
//            gene.setNote(note);
//            gene.setVelocity(velocity);
//            gene.setKey(key);
//            individual.addGene(gene);
//        }
//        return individual;
//    }
//
//    public int getRandomTick(int low,int high){
//        int x = (int) Math.round((Math.random()*((high-low)+1))+low);
//        return x;
//    }
//
//    public boolean getRandomNote(){
//        return Math.random()<0.5;
//    }
//
//    public int getRandomKey(int low,int high){
//        int x = (int) Math.round((Math.random()*((high-low)+1))+low);
//        return x;
//    }
//
//    public int getRandomVelocity(int low,int high){
//        int x = (int) Math.round((Math.random()*((high-low)+1))+low);
//        return x;
//    }
//    public void printIndividual(Individual individual){
//        ArrayList<Genotype> ind= individual.getIndividual();
//        for(int i=0;i<ind.size();i++){
//            Genotype gene = ind.get(i);
//            String note = gene.isNote()?"on":"off";
//            System.out.println("gene "+i+" tick="+gene.getTick()+" key="+gene.getKey()+" Note="+note+" velocity="+gene.getVelocity());
//        }
//    }
}

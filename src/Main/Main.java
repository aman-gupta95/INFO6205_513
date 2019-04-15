package Main;

import GA.Genotype;
import GA.Individual;
import GA.Populate;
import ProcessSound.SeeMidi;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    private static final int POOL_SIZE = 500;
    private static final int MAX_VELOCITY = 126;
    private static final int MAX_KEY = 127;

    private static ArrayList<Individual> poolOfSounds;
    private static ArrayList<Genotype> originalSound;

    public static void main(String args[]) throws Exception {
        SeeMidi midi = new SeeMidi();
        originalSound = midi.parseMidi();
        int POP_SIZE = midi.getTrackSize();
        int TICK_LENGTH = midi.getTickLength();
        long MAX_TICK = midi.getTick();
        poolOfSounds = Populate.initPool(POOL_SIZE, POP_SIZE, MAX_KEY, MAX_VELOCITY, MAX_TICK);
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

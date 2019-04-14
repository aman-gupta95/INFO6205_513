package Main;

import GA.Genotype;
import GA.Individual;

import java.util.ArrayList;

public class Main {


    public Individual generateIndividual(){
        Individual individual=new Individual();
        for(int i= 0;i<200;i++){
            int tick = getRandomTick(0,1100);
            boolean note = getRandomNote();
            int key = getRandomKey(1,100);
            int velocity = getRandomVelocity(10,100);
            Genotype gene = new Genotype(tick,key,note,velocity);
            individual.addGene(gene);

        }
        return individual;
    }

    public int getRandomTick(int low,int high){
        int x = (int) Math.round((Math.random()*((high-low)+1))+low);
        return x;
    }

    public boolean getRandomNote(){
        return Math.random()<0.5;
    }

    public int getRandomKey(int low,int high){
        int x = (int) Math.round((Math.random()*((high-low)+1))+low);
        return x;
    }

    public int getRandomVelocity(int low,int high){
        int x = (int) Math.round((Math.random()*((high-low)+1))+low);
        return x;
    }
    public void printIndividual(Individual individual){
        ArrayList<Genotype> ind= individual.getIndividual();
        for(int i=0;i<ind.size();i++){
            Genotype gene = ind.get(i);
            String note = gene.isNote()?"on":"off";
            System.out.println("gene "+i+" tick="+gene.getTick()+" key="+gene.getKey()+" Note="+note+" velocity="+gene.getVelocity());
        }
    }
}

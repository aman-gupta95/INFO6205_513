package Main;

import GA.Genotype;
import GA.Individual;

public class Fitness {

    public void computeFitnessIndividual(Individual goal, Individual random){
        long total=0;
        for(int i=0;i<goal.getIndividual().size();i++){
            Genotype parent = goal.getGene(i);
            Genotype child = random.getGene(i);
            long fitness = computeFitnessGene(parent,child);
            child.setGene_fitness(fitness);
            total+=fitness;
        }
        random.setFitness(total);
    }

public long computeFitnessGene(Genotype parent,Genotype child){
    long fitness = 0, diff_key=0,diff_vel=0;
    if(parent.isNote()==child.isNote()){
        fitness++;
    }
    diff_key=Math.abs(parent.getKey()-child.getKey());
    if(diff_key==0){
        fitness++;
    }
    diff_vel=Math.abs(parent.getVelocity()-child.getVelocity());
    if(diff_vel==0){
        fitness++;
    }
    if(fitness==3){
        fitness++;
    }
    if(diff_vel==0 && diff_key==0){
        fitness++;
    }
    if(diff_vel==0 && parent.isNote()==child.isNote()){
        fitness++;
    }
    if(diff_key==0 && parent.isNote()==child.isNote()){
        fitness++;
    }

    return fitness;

}
}

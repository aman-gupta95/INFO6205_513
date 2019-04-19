package Main;

import GA.Genotype;
import GA.Individual;

public class Fitness {

    public void computeFitnessIndividual(Individual goal, Individual random){
        long total=0;
        for(int i=0;i<10;i++){
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
        diff_key=parent.getKey()-child.getKey();
        diff_vel=parent.getVelocity()-child.getVelocity();
        fitness+=diff_key+diff_vel;
        return fitness;

    }
}

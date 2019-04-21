package Main;

import GA.Genotype;
import GA.Individual;

public class Fitness {
    //function to compute individual fitness
    public static void computeFitnessIndividual(Individual goal, Individual random){
        long total=0;
        for(int i=0;i<goal.getIndividual().size();i++){
            Genotype parent = goal.getGene(i);
            Genotype child = random.getGene(i);
            long fitness = computeFitnessGene(parent,child);//compute fitness of a particular gene
            child.setGene_fitness(fitness);//set the fitness of child gene
            total+=fitness;//add fitness of gene to indivial
        }
        random.setFitness(total);//set the final fitness of individual which got generated after adding fitness of all genes
    }
    //function to compute gene fitness
    public static long computeFitnessGene(Genotype parent,Genotype child){
        long fitness = 0, diff_key=0,diff_vel=0;
        if(parent.isNote()==child.isNote()){//when parent note is same as child note
            fitness++;
        }
        diff_key=Math.abs(parent.getKey()-child.getKey());//when parent key is same as child key
        if(diff_key==0){
            fitness++;
        }
        diff_vel=Math.abs(parent.getVelocity()-child.getVelocity());//when parent velocity is same as child velocity
        if(diff_vel==0){
            fitness++;
        }
        if(fitness==3){//when all three properties are same
            fitness++;
        }
        if(diff_vel==0 && diff_key==0){//when key and velocity of both genes are same
            fitness++;
        }
        if(diff_vel==0 && parent.isNote()==child.isNote()){//when note and velocity of both genes are same
            fitness++;
        }
        if(diff_key==0 && parent.isNote()==child.isNote()){//when key and note of both genes are same
            fitness++;
        }

        return fitness;//fitness of individual

    }
}

package GA;

import java.util.ArrayList;

public class Populate {
    public static ArrayList<Individual> initPool(int poolSize, int popSize, int maxKey, int maxVelocity, int maxTick){
        ArrayList<Individual> soundPopulation = new ArrayList<>();
        for(int i = 0; i < poolSize; i++){
            ArrayList<Genotype> genes = new ArrayList<>();
            for(int j = 0; j < popSize; j++){
                genes.add(Mutation.getRandomGenes(maxKey, maxVelocity, maxTick));
            }
            soundPopulation.add(new Individual(genes));
        }
        return soundPopulation;
    }
}

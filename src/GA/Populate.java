package GA;

import java.util.ArrayList;
import java.util.Random;

public class Populate {
    //helps to randomly generate pool of individuals with random genes of fixed length with given input parameters
    public static ArrayList<Individual> initPool(int poolSize, int popSize, int maxKey, int maxVelocity, long maxTick){
        ArrayList<Individual> soundPopulation = new ArrayList<>();
        for(int i = 0; i < poolSize; i++){
            ArrayList<Genotype> genes = new ArrayList<>();
            for(int j = 0; j < popSize; j++){
                genes.add(getRandomGenes(maxKey, maxVelocity));//generate random gene and add to individual
            }
            soundPopulation.add(new Individual(genes));
        }
        return soundPopulation;//return pool
    }

    //help to generate random gene with given parameter ranges
    public static Genotype getRandomGenes(int maxKey, int maxVelocity){
        Random random = new Random();
        Genotype genotype = new Genotype();
        genotype.setKey(random.nextInt(maxKey));
        genotype.setVelocity(random.nextInt(maxVelocity)+1);
        genotype.setNote(random.nextBoolean());
        return genotype;
    }
}

package GA;

import java.util.Random;

public class Mutation {

    public static Genotype getRandomGenes(int maxKey, int maxVelocity, long maxTick){
        Random random = new Random();
        Genotype genotype = new Genotype();
        genotype.setKey(random.nextInt(maxKey));
        genotype.setVelocity(random.nextInt(maxVelocity)+1);
        genotype.setTick(random.nextInt((int)maxTick));
        genotype.setNote(random.nextBoolean());
        return genotype;
    }
}

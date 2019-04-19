package GA;

import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public class Mutation {

    public static Genotype getRandomGenes(int maxKey, int maxVelocity){
        Random random = new Random();
        Genotype genotype = new Genotype();
        genotype.setKey(random.nextInt(maxKey));
        genotype.setVelocity(random.nextInt(maxVelocity)+1);
        genotype.setNote(random.nextBoolean());
        return genotype;
    }

    public static void mutate(ArrayList<Genotype> geneList,int maxKey, int maxVelocity,Individual original) {
        Random random=new Random();
        int position = random.nextInt(geneList.size());
        Genotype gene = geneList.get(position);

        switch (random.nextInt(6)) {
            case 0:
                // remove one Gene and add new Gene
                gene = original.getGene(position);
                geneList.remove(position);
                geneList.add(gene);
                break;
            case 1:
                // swap two genes from the list
                Collections.swap(geneList, random.nextInt(geneList.size()), position);
                break;
            case 2:
                // change any one's velocity
                gene.setVelocity(random.nextInt(maxVelocity)+1);
                geneList.set(position, gene);
                break;
            case 3:
                // change any one's key
                gene.setKey(random.nextInt(maxKey)+1);
                geneList.set(position, gene);
                break;
            case 4:
                // change any one's note
                gene.setNote(!gene.isNote());
                geneList.set(position, gene);
                break;
            case 5:
                // replace one with new gene
                gene = getRandomGenes(maxKey,maxVelocity);
                geneList.set(position, gene);
                break;
        }

    }

}
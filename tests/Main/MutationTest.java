package Main;

import GA.Genotype;
import GA.Individual;
import GA.Mutation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MutationTest {

    @Test
    public void generateRandomGene() throws Exception {
        Genotype gene1= Mutation.getRandomGenes(127, 128);
        Genotype gene2= Mutation.getRandomGenes(127, 128);
        assertTrue((gene1.getKey()!=gene2.getKey())&&(gene1.getVelocity()!=gene2.getVelocity()));
    }

    @Test
    public void mutateTest() throws Exception{
        Genotype gene1= Mutation.getRandomGenes(127, 128);
        Genotype gene2= Mutation.getRandomGenes(127, 128);
        ArrayList<Genotype> originalGenes = new ArrayList<>();
        originalGenes.add(gene1);
        originalGenes.add(gene2);
        Individual individual = new Individual(originalGenes);
        ArrayList<Genotype> randomGenes = new ArrayList<>();
        randomGenes.add(Mutation.getRandomGenes(127, 128));
        randomGenes.add(Mutation.getRandomGenes(127, 128));
        Mutation.mutate(randomGenes,127,128,individual);
        Genotype gene_1 = randomGenes.get(0);
        Genotype gene_2 = randomGenes.get(1);
        assertTrue((gene1.getKey()!=gene_1.getKey())&&(gene1.getVelocity()!=gene_1.getVelocity()));
        assertTrue((gene2.getKey()!=gene_2.getKey())&&(gene2.getVelocity()!=gene_2.getVelocity()));
    }
}

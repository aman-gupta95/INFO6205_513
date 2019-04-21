package Main;

import GA.CrossOver;
import GA.Genotype;
import GA.Individual;
import GA.Mutation;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class CrossoverTest {

    @Test
    public void mateBest(){
        Genotype gene1= Mutation.getRandomGenes(127, 128);
        Genotype gene2= Mutation.getRandomGenes(127, 128);
        Genotype gene5= Mutation.getRandomGenes(127, 128);
        ArrayList<Genotype> originalGenes = new ArrayList<>();
        originalGenes.add(gene1);
        originalGenes.add(gene2);
        originalGenes.add(gene5);
        Individual original = new Individual(originalGenes);
        Genotype gene3= Mutation.getRandomGenes(127, 128);
        Genotype gene4= Mutation.getRandomGenes(127, 128);
        Genotype gene6= Mutation.getRandomGenes(127, 128);
        ArrayList<Genotype> randomGenes = new ArrayList<>();
        randomGenes.add(gene3);
        randomGenes.add(gene4);
        randomGenes.add(gene6);
        Individual random = new Individual(randomGenes);
        ArrayList<Individual> individual = new ArrayList<>();
        individual.add(random);
        ArrayList<Individual> ind = CrossOver.mateBest(individual,1,2, 1,127, 128, original);
        assertTrue(ind.get(0).getFitness()==individual.get(0).getFitness());
        assertFalse(ind.get(0).getFitness()!=individual.get(0).getFitness());
    }
}

package GA;
import java.util.ArrayList;

public class Individual implements Comparable<Individual>{

    private ArrayList<Genotype> individual;
    private Long fitness;

    public Long getFitness() {
        return fitness;
    }

    public void setFitness(Long fitness) {
        this.fitness = Math.abs(fitness);
    }

    public Individual(ArrayList<Genotype> genes) {
        this.individual = genes;
    }

    public ArrayList<Genotype> getIndividual() {
        return individual;
    }

    public void setIndividual(ArrayList<Genotype> individual) {
        this.individual = individual;
    }

    public void addGene(Genotype gene){
        individual.add(gene);
    }

    public Genotype getGene(int i){
        return individual.get(i);
    }


    @Override
    public int compareTo(Individual o) {
        // TODO Auto-generated method stub
        return fitness.compareTo(o.getFitness());
    }

    @Override
    public String toString() {
        return fitness.toString();
    }
}

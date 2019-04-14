package GA;
import java.util.ArrayList;
public class Individual {

    ArrayList<Genotype> individual;

    public Individual() {
        this.individual = new ArrayList<Genotype>();
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
}

package GA;
import java.util.ArrayList;
import java.util.Random;
public class CrossOver {
    private static Individual crossOver(ArrayList<Individual> newPool, int best, int maxKey, int maxVelocity, int mutationCount,int goal_length,Individual original) {
        // get random 2 from elite
        Random random = new Random();
        int first = random.nextInt(best);
        int second = random.nextInt(best);
        Individual individual = null;
        while (first == second) {
            second = random.nextInt(best);
        }
        // mate the two
        switch (random.nextInt(2)) {
            case 0: {//just randomly shuffle
                individual = crossOver(newPool.get(first), newPool.get(second), mutationCount, maxKey, maxVelocity,original);
            }
            case 1: {//generate by random index
                individual = crossOver(newPool.get(first), newPool.get(second),mutationCount, maxKey, maxVelocity, goal_length,original);
            }
        }
        return individual;
    }

    //this function to crossover to generate new individual by selecting its gene randomly from parents
    public static Individual crossOver(Individual first, Individual second, int mutationCount, int maxKey, int maxVelocity,Individual original) {
        Random random = new Random();
        ArrayList<Genotype> genes = new ArrayList<>();
        Individual individual = new Individual(genes);
        for (int i = 0; i < first.getIndividual().size(); i++) {
            switch (random.nextInt(2)) {//helps to randomly select parent for each gene
                case 0: {
                    individual.getIndividual().add(new Genotype(first.getIndividual().get(i)));
                    break;
                }
                case 1: {
                    individual.getIndividual().add(new Genotype(second.getIndividual().get(i)));
                    break;
                }
            }
        }

        for (int i = 0; i < mutationCount; i++) {
            Mutation.mutate(genes, maxKey, maxVelocity,original);//make a call to mutation
        }
        return individual;
    }

    //this function to crossover to generate new individual by selecting random index where all genes before that index from parent one and after that index from parent 2
    public static Individual crossOver(Individual first, Individual second, int mutationCount, int maxKey, int maxVelocity, int goal_length, Individual original) {
        Random random = new Random();
        ArrayList<Genotype> genes = new ArrayList<>();
        int temp = random.nextInt(goal_length);//random index selection
        for (int i = 0; i < temp; i++) {
            genes.add(new Genotype(first.getIndividual().get(i)));
        }
        for (int i = temp; i < goal_length; i++) {
            genes.add(new Genotype(second.getIndividual().get(i)));
        }
        Individual individual = new Individual(genes);
        for (int i = 0; i < mutationCount; i++) {
            Mutation.mutate(genes, maxKey, maxVelocity,original);//make a call to mutation
        }
        return individual;
    }

    //creation of new generation. Here first inserting all best old individuals and then populating new individuals by cross over and mutation
    public static ArrayList<Individual> mateBest(ArrayList<Individual> pool, int best, int goal_length, int mutationCount, int maxKey, int maxVelocity,Individual original) {
        ArrayList<Individual> newPool = new ArrayList<>();
        for (int i = 0; i < pool.size(); i++) {
            if (i < best) {
                newPool.add(pool.get(i));//Selection process
            } else {
                newPool.add(crossOver(newPool, best, maxKey, maxVelocity, mutationCount,goal_length,original));//cross-over for new generation
            }
        }
        return newPool;
    }
}

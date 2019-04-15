package GA;

public class Genotype implements Comparable<Genotype>{

    private int tick;
    private boolean note;
    private int key;
    private int velocity;
    private Long fitness;


    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public boolean isNote() {
        return note;
    }

    public void setNote(boolean note) {
        this.note = note;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }


    public Long getFitness() {
        return fitness;
    }

    public void setFitness(Long fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Genotype gene){
        return fitness.compareTo(gene.getFitness());
    }
}

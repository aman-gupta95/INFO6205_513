package GA;

public class Genotype implements Comparable<Genotype>{

    private int tick;
    private boolean note;
    private String noteName;
    private int octave;
    private int key;
    private int velocity;
    private Long fitness;

    public Genotype(Genotype gene) {
        this.tick = gene.getTick();
        this.key = gene.getKey();
        this.note = gene.isNote();
        this.noteName = gene.getNoteName();
        this.velocity = gene.getVelocity();
        this.octave = gene.getOctave();
    }


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

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
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

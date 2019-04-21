package GA;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CrossOverTest {

    @Test
    public void crossOver() {
        ArrayList<Genotype> glist_1 = new ArrayList<>();
        Genotype g1= new Genotype();
        g1.setNote(false);
        g1.setVelocity(46);
        g1.setKey(76);

        Genotype g2 = new Genotype();
        g2.setNote(true);
        g2.setVelocity(36);
        g2.setKey(76);
        glist_1.add(g1);
        glist_1.add(g2);
        Individual i1 = new Individual(glist_1);

        ArrayList<Genotype> glist_2 = new ArrayList<>();
        Genotype g3= new Genotype();
        g3.setNote(false);
        g3.setVelocity(46);
        g3.setKey(76);

        Genotype g4 = new Genotype();
        g4.setNote(true);
        g4.setVelocity(36);
        g4.setKey(76);
        glist_2.add(g3);
        glist_2.add(g4);
        Individual i2 = new Individual(glist_2);


    }

    @Test
    public void crossOver1() {
    }

}
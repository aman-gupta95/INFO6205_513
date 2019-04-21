package Main;

import GA.Genotype;
import GA.Individual;
import GA.Mutation;
import Main.Fitness;
import ProcessSound.SeeMidi;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FitnessTest {

    @Test
    public void computeFitnessGene_onlyVelocity() {
        Genotype g1= new Genotype();
        g1.setNote(false);
        g1.setVelocity(46);
        g1.setKey(76);

        Genotype g2 = new Genotype();
        g2.setNote(true);
        g2.setVelocity(46);
        g2.setKey(87);

        assert (Fitness.computeFitnessGene(g1,g2)==1);
    }

    @Test
    public void computeFitnessGene_onlyKey() {
        Genotype g1= new Genotype();
        g1.setNote(false);
        g1.setVelocity(46);
        g1.setKey(76);

        Genotype g2 = new Genotype();
        g2.setNote(true);
        g2.setVelocity(36);
        g2.setKey(76);

        assert (Fitness.computeFitnessGene(g1,g2)==1);
    }

    @Test
    public void computeFitnessGene_onlyNote() {
        Genotype g1= new Genotype();
        g1.setNote(true);
        g1.setVelocity(46);
        g1.setKey(76);

        Genotype g2 = new Genotype();
        g2.setNote(true);
        g2.setVelocity(66);
        g2.setKey(87);

        assert (Fitness.computeFitnessGene(g1,g2)==1);
    }

    @Test
    public void computeFitnessGene_all() {
        Genotype g1= new Genotype();
        g1.setNote(true);
        g1.setVelocity(32);
        g1.setKey(76);

        Genotype g2 = new Genotype();
        g2.setNote(true);
        g2.setVelocity(32);
        g2.setKey(76);

        assert (Fitness.computeFitnessGene(g1,g2)==7);
    }

    @Test
    public void computeFitnessGene_onlyVel_and_key() {
        Genotype g1= new Genotype();
        g1.setNote(true);
        g1.setVelocity(46);
        g1.setKey(76);

        Genotype g2 = new Genotype();
        g2.setNote(false);
        g2.setVelocity(46);
        g2.setKey(76);

        assert (Fitness.computeFitnessGene(g1,g2)==3);
    }

    @Test
    public void computeFitnessGene_onlyNote_and_Vel() {
        Genotype g1= new Genotype();
        g1.setNote(true);
        g1.setVelocity(66);
        g1.setKey(76);

        Genotype g2 = new Genotype();
        g2.setNote(true);
        g2.setVelocity(66);
        g2.setKey(87);

        assert (Fitness.computeFitnessGene(g1,g2)==3);
    }

    @Test
    public void computeFitnessGene_onlyNote_key() {
        Genotype g1= new Genotype();
        g1.setNote(true);
        g1.setVelocity(46);
        g1.setKey(87);

        Genotype g2 = new Genotype();
        g2.setNote(true);
        g2.setVelocity(66);
        g2.setKey(87);

        assert (Fitness.computeFitnessGene(g1,g2)==3);
    }



    @Test
    public void computeFitnessIndividual_less() throws Exception{
        SeeMidi midi = new SeeMidi();
        Individual original=null,i1=null,i2=null;
        try {
            ArrayList<Genotype> midi1 = midi.parseMidi("testAudio/generatedAudio100.mid");
            i1 = new Individual(midi1);
            ArrayList<Genotype> midi2 = midi.parseMidi("testAudio/generatedAudio1000.mid");
            i2 = new Individual(midi2);
            ArrayList<Genotype> originalSound = midi.parseMidi("audio.mid");
            original = new Individual(originalSound);

        }catch (Exception ex){
            System.out.println("File not found");
        }
        Fitness.computeFitnessIndividual(original, i1);
        Long fitness1 = i1.getFitness();
        Fitness.computeFitnessIndividual(original, i2);
        Long fitness2 = i2.getFitness();
        assert(fitness2>fitness1);


    }

    @Test
    public void computeFitnessIndividual_greater() throws Exception {
        SeeMidi midi = new SeeMidi();
        Individual original=null,i1=null,i2=null;
        try {
            ArrayList<Genotype> midi1 = midi.parseMidi("testAudio/generatedAudio2700.mid");
            i1 = new Individual(midi1);
            ArrayList<Genotype> midi2 = midi.parseMidi("testAudio/generatedAudio400.mid");
            i2 = new Individual(midi2);
            ArrayList<Genotype> originalSound = midi.parseMidi("audio.mid");
            original = new Individual(originalSound);

        }catch (Exception ex){
            System.out.println("File not found");
        }
        Fitness.computeFitnessIndividual(original, i1);
        Long fitness1 = i1.getFitness();
        Fitness.computeFitnessIndividual(original, i2);
        Long fitness2 = i2.getFitness();
        assert(fitness1>fitness2);
    }

}
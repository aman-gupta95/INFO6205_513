package ProcessSound;

import GA.Genotype;
import GA.Individual;

import javax.sound.midi.*;
import java.io.File;
import java.util.List;

public class CreateMidi {


    private static Sequence sequence;
    private static String SOUND_SAVE_PATH = "SavedSounds/generatedAudio";
    private static String ORIGINAL_PATH = "OriginalSounds/Audio";


    public static void generateMidi(List<Long> ticks, Individual ind, int tickLength, int generation) {
        List<Genotype> genes = ind.getIndividual();
        try {
            sequence = new Sequence((float) 0.0, tickLength);
            Track track = sequence.createTrack();
            File file;
            for (int i = 0; i < genes.size(); i++) {
                MidiMessage sm = new ShortMessage(genes.get(i).isNote() ? 0x90 : 0x80, 0, genes.get(i).getKey(), genes.get(i).getVelocity());
                MidiEvent event = new MidiEvent(sm, ticks.get(i));
                track.add(event);
            }

            file = new File(SOUND_SAVE_PATH + generation + ".mid");

            MidiSystem.write(sequence, 0, file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}










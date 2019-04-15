package ProcessSound;

import GA.Genotype;

import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class SeeMidi {
    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;
    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private static final String AUDIO = "audio.mid";
    private static Sequence sequence;

    public ArrayList<Genotype> parseMidi() throws Exception {

        this.sequence = MidiSystem.getSequence(new File(AUDIO));
        ArrayList<Genotype> originalSound = new ArrayList<>();
        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            for (int i=0; i < track.size(); i++) {
                Genotype gene = new Genotype();
                MidiEvent event = track.get(i);
                gene.setTick(event.getTick());
//                System.out.print("@" + String.valueOf(event.getTick()) + " tickLength: "+String.valueOf(sequence.getResolution()));
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
//                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
//                        int octave = (key / 12)-1;
//                        int note = key % 12;
//                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        gene.setKey(key);
                        gene.setVelocity(velocity);
                        gene.setNote(true);
//                        System.out.println("Note on, " + noteName + " octave="+octave + " key=" + key + " velocity: " + velocity);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
//                        int octave = (key / 12)-1;
//                        int note = key % 12;
//                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        gene.setKey(key);
                        gene.setVelocity(velocity);
                        gene.setNote(false);
//                        System.out.println("Note off, " + noteName + " octave="+ octave + " key=" + key + " velocity: " + velocity);
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
                originalSound.add(gene);
            }

            System.out.println();
        }
        return originalSound;
    }

    public int getTrackSize(){
        Track track = sequence.getTracks()[0];
        return track.size();
    }

    public int getTickLength(){
        return sequence.getResolution();
    }

    public long getTick(){
        Track track = sequence.getTracks()[0];
        MidiEvent event = track.get(track.size()-1);
        return event.getTick();
    }
        //i have commented above code to test this
//        System.out.println("--------------------------------------------------------------------------------------------");
//        Main main = new Main(); //this is just a sample data we need to fix the range yet and it generate 200 genes for a particular individual
//        main.printIndividual(main.generateIndividual());

}

package ProcessSound;

import GA.Genotype;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class SeeMidi {
    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;
    private static final String AUDIO = "audio.mid";
    private static Sequence sequence;
    private  int command;
    private  List<Long> ticks;

    public ArrayList<Genotype> parseMidi(String filename) throws Exception {
        int[] arr = MidiSystem.getMidiFileTypes(MidiSystem.getSequence(new File(filename)));
        for(int a: arr){
            System.out.print(a+" ");
        }
        System.out.println();
        this.sequence = MidiSystem.getSequence(new File(filename));
        ArrayList<Genotype> originalSound = new ArrayList<>();
        ticks = new ArrayList<>();
        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            for (int i=0; i < track.size(); i++) {
                Genotype gene = new Genotype();
                MidiEvent event = track.get(i);
                ticks.add(event.getTick());
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        int velocity = sm.getData2();
                        gene.setKey(key);
                        gene.setVelocity(velocity);
                        gene.setNote(true);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
                        int velocity = sm.getData2();
                        gene.setKey(key);
                        gene.setVelocity(velocity);
                        gene.setNote(false);
                    } else {
                        System.out.println("Command:" + sm.getCommand()+" "+ sm.getChannel());
                        this.command = sm.getCommand();
                    }
                } else {
                    System.out.println("Other message: " + message.getClass()+" " +message.toString());

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

    public  int getCommand() {
        return command;
    }

    public  void setCommand(int command) {
        this.command = command;
    }

    public  List<Long> getTicks() {
        return ticks;
    }

    public  void setTicks(List<Long> ticks) {
        this.ticks = ticks;
    }


}

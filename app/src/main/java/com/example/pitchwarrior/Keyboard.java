package com.example.pitchwarrior;

import android.media.SoundPool;

public class Keyboard {

    private Note cSha;
    private Note dNat;
    private Note dSha;
    private Note eNat;
    private Note fNat;
    private Note fSha;
    private Note gNat;
    private Note gSha;
    private Note aNat;
    private Note aSha;
    private Note bNat;
    // noteList is going to be the list which connects the random int to the notes to be played.
    private Note[] noteList = new Note[12];

    //To play note sounds.
    private SoundPool soundPool;

    private int priority;

    public Keyboard()
    {
        Note cNat = new Note("C", 1);
        cSha = new Note("C#", 2);
        cSha.setAltName("Db");
        dNat = new Note("D", 3);
        dSha = new Note("D#", 4);
        dSha.setAltName("Eb");
        eNat = new Note("E", 5);
        fNat = new Note("F", 6);
        fSha = new Note("F#", 7);
        fSha.setAltName("Gb");
        gNat = new Note("G", 8);
        gSha = new Note("G#", 9);
        gSha.setAltName("Ab");
        aNat = new Note("A", 10);
        aSha = new Note("A#", 11);
        aSha.setAltName("Bb");
        bNat = new Note("B", 12);

        noteList = new Note[]{cNat, cSha, dNat, dSha, eNat, fNat,
                fSha, gNat, gSha, aNat, aSha, bNat};
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public static Note getNote(int noteNumber)
    {
        Note returnNote = new Note();
        returnNote.setNumber(noteNumber);
        return returnNote;
    }
}

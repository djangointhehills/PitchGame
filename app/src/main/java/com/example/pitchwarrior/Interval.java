package com.example.pitchwarrior;

public class Interval
{

    private IntervalName name;
    private Note firstNote;
    private Note secondNote;
    private int number;
    private boolean correct;
    private Direction direction;

    public enum Direction
    {
        UP, DOWN;
    }

    public enum IntervalName
    {
        UNISON, MINOR_SECOND, MAJOR_SECOND, MINOR_THIRD, MAJOR_THIRD, PERFECT_FOURTH, TRITONE,
        PERFECT_FIFTH, MAJOR_FIFTH, MINOR_SIXTH, MAJOR_SIXTH, MINOR_SEVENTH, MAJOR_SEVENTH, OCTAVE;
    }

    public Interval(IntervalName name)
    {
        this.name = name;
    }

    public Interval(Note firstNote, Note secondNote)
    {
        this.firstNote = firstNote;
        setSecondNote(secondNote);
    }

    public Interval(Note startNote)
    {
        this.firstNote = firstNote;
    }

    public Note getFirstNote()
    {
        return firstNote;
    }

    public void setFirstNote(Note firstNote)
    {
        this.firstNote = firstNote;
    }

    public boolean isCorrect()
    {
        return correct;
    }

    public void setCorrect(boolean correct)
    {
        this.correct = correct;
    }

    public IntervalName getName()
    {
        return name;
    }

    public Note getSecondNote()
    {
        return secondNote;
    }

    public void setSecondNote(Note secondNote)
    {
        this.secondNote = secondNote;
        if (firstNote.getNumber() < secondNote.getNumber())
        {
            direction = Direction.UP;
        } else if (firstNote.getNumber() > secondNote.getNumber())
        {
            direction = Direction.DOWN;
        } else
        {
            direction = null;
        }
        number = Math.abs(firstNote.getNumber() - secondNote.getNumber());
        switch (number)
        {
            case 0:
                if (firstNote.getNumber() == secondNote.getNumber())
                {
                    name = IntervalName.UNISON;
                    break;
                } else
                {
                    name = IntervalName.OCTAVE;
                    break;
                }
            case 1:
                name = IntervalName.MINOR_SECOND;
                break;
            case 2:
                name = IntervalName.MAJOR_SECOND;
                break;
            case 3:
                name = IntervalName.MINOR_THIRD;
                break;
            case 4:
                name = IntervalName.MAJOR_THIRD;
                break;
            case 5:
                name = IntervalName.PERFECT_FOURTH;
                break;
            case 6:
                name = IntervalName.TRITONE;
                break;
            case 7:
                name = IntervalName.PERFECT_FIFTH;
                break;
            case 8:
                name = IntervalName.MINOR_SIXTH;
                break;
            case 9:
                name = IntervalName.MAJOR_SIXTH;
                break;
            case 10:
                name = IntervalName.MINOR_SEVENTH;
                break;
            case 11:
                name = IntervalName.MAJOR_SEVENTH;
                break;
        }
    }
}

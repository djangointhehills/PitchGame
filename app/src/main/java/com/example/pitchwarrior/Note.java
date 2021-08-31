package com.example.pitchwarrior;

import android.widget.Button;

public class Note
{
    private String name;
    private String altName;
    private int number;
    private Button button;

    public Note()
    {
    }

    public Note(String name, int number)
    {
        this.name = name;
        this.number = number;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public Button getButton()
    {
        return button;
    }

    public void setButton(Button button)
    {
        this.button = button;
    }

    public String getAltName()
    {
        return altName;
    }

    public void setAltName(String altName)
    {
        this.altName = altName;
    }
}

package com.example.pitchwarrior;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;

public class Record extends Activity {

    private ArrayList<Score> recordsArrayList = new ArrayList<>();
    private Calendar dateTime;



    public void Record()
    {
        dateTime = Calendar.getInstance();
        dateTime.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND);
    }

    public void addScore(Score score)
    {
        int i = 0;
        while (i < recordsArrayList.size() && recordsArrayList.get(i).getAccuracy() > score.getAccuracy())
        {
            i++;
        }
        recordsArrayList.add(i, score);
    }

    public String toString()
    {
        //StringBuilder recordString = new StringBuilder();
        Formatter stringFormat = new Formatter();

        String returnString = stringFormat.format("%-10s | %-10s | %-10s | %-10s\n", "Rank", "Accuracy", "Date", "Time").toString();

        for (int i = 0; i < recordsArrayList.size(); i++)
        {
            returnString = stringFormat.format("%-10s | %-10s | %-10s | %-10s", i,
                    recordsArrayList.get(i).getAccuracy(), recordsArrayList.get(i).getGameType().gameLength,
                    "Time").toString();
            if (i != recordsArrayList.size() - 1)
            {
                returnString.format("\n");
            }
        }
        return returnString;
    }

    public String getDateTime()
    {
        return String.valueOf(dateTime.getTime());
    }
}

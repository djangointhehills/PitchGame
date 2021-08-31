package com.example.pitchwarrior;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//TODO Create a personal results page, that track the user's individual progress. Maybe implement graph representation.

public class Results extends AppCompatActivity
{
    TextView rank;
    TextView accuracy;
    TextView correct;
    TextView incorrect;
    TextView bestStreak;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        rank = (TextView) findViewById(R.id.resulstRankTextView);
        accuracy = (TextView) findViewById(R.id.resultsAccuracyCorrectTextView);
        correct = (TextView) findViewById(R.id.resultsCorrectTextView);
        incorrect = (TextView) findViewById(R.id.resultsIncorrectTextView);
        bestStreak = (TextView) findViewById(R.id.resultsBestStreakTextView);

        rank.setText(String.format("Rank    %s", MainActivity.database.getRank(Game.gameScore)));
        accuracy.setText(String.format("Accuracy    %s", Game.gameScore.getAccuracy()));
        correct.setText(String.format("Number of correct notes    %s", Game.gameScore.getCorrect()));
        incorrect.setText(String.format("Number of incorrect note    %s", Game.gameScore.getIncorrect()));
        bestStreak.setText(String.format("Best streak    %s", Game.gameScore.getBestStreak()));

        Button viewRecords = (Button) findViewById(R.id.resultsViewRecords);
        viewRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRecords = new Intent(Results.this, ViewListContents.class);
                startActivity(intentRecords);
            }
        });
    }
}
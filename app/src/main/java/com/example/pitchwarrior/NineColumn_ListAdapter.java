package com.example.pitchwarrior;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//TODO get the column width wrap content.

public class NineColumn_ListAdapter extends ArrayAdapter<Score>
{
    private LayoutInflater mInflater;
    private ArrayList<Score> scores;
    private int mViewResourceId;

    public NineColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<Score> scores)
    {
        super(context, textViewResourceId, scores);
        this.scores = scores;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents)
    {
        convertView = mInflater.inflate(mViewResourceId, null);

        Score score = scores.get(position);

        if (score != null)
        {
            TextView rank = (TextView) convertView.findViewById(R.id.rank);
            TextView username = (TextView) convertView.findViewById(R.id.userName);
            TextView points = (TextView) convertView.findViewById(R.id.points);
            TextView accuracy = (TextView) convertView.findViewById(R.id.accuracy);
            TextView correct = (TextView) convertView.findViewById(R.id.correct);
            TextView incorrect = (TextView) convertView.findViewById(R.id.incorrect);
            TextView bestStreak = (TextView) convertView.findViewById(R.id.streak);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView gamytype = (TextView) convertView.findViewById(R.id.gameType);


            if (rank != null)
            {
                rank.setText(String.valueOf(score.getRank()));
            }
            if (username != null)
            {
                username.setText(score.getUsername());
            }
            if (points != null)
            {
                points.setText(String.valueOf(score.getPoints()));
            }
            if (accuracy != null)
            {
                accuracy.setText(String.valueOf(score.getAccuracy()));
            }
            if (correct != null)
            {
                correct.setText(String.valueOf(score.getCorrect()));
            }
            if (incorrect != null)
            {
                incorrect.setText(String.valueOf(score.getIncorrect()));
            }
            if (bestStreak != null)
            {
                bestStreak.setText(String.valueOf(score.getBestStreak()));
            }
            if (date != null)
            {
                date.setText(score.getDate());
            }
            if (time != null)
            {
                time.setText(score.getTime());
            }
            if (gamytype != null)
            {
                gamytype.setText(score.getGameType().toString());
            }
        }
        return convertView;
    }

}

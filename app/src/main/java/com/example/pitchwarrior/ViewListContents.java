package com.example.pitchwarrior;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity

    //TODO Highlight most recent record?
    //TODO Have the ability to click on a record to get more data on that result.

{
    Database myDB;
    ArrayList<Score> scoreList;
    ListView listview;
    TextView heading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);
        heading = findViewById(R.id.viewContentsHeadingTextView);
        switch (Game.gameScore.getGameType())
        {
            case THIRTYSECONDS:
                heading.setText("30 Second Game Records");
                break;
            case ONEMIN:
                heading.setText("1 Minute Game Records");
                break;
            case TWOMIN:
                heading.setText("2 Minute Game Records");
                break;
        }
        myDB = MainActivity.database;
        scoreList =  (ArrayList<Score>) MainActivity.database.getAllScores(Game.gameScore.getGameType());
        NineColumn_ListAdapter adapter = new NineColumn_ListAdapter(this, R.layout.list_adapter_view, scoreList);
        listview = (ListView) findViewById(R.id.listView);
                listview.setAdapter(adapter);
    }
}

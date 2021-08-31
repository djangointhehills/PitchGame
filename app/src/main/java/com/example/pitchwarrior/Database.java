package com.example.pitchwarrior;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

//TODO have an online database of scores for users to compare their own to. Only keep users score locally. This might mean removing the login as the default start up page.
//TODO Response time for correct/incorrect answer.
//TODO What intervals you most commonly mistake for which.
//TODO What intervals you get wrong.
//TODO What interval proceeds these intervals.
//TODO What register you tend to perform better/worse in.


public class Database extends SQLiteOpenHelper
{
    public static final String SCORE_TABLE = "RECORDS_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_POINTS = "POINTS";
    public static final String COLUMN_ACCURACY = "ACCURACY";
    public static final String COLUMN_CORRECT = "CORRECT";
    public static final String COLUMN_INCORRECT = "INCORRECT";
    public static final String COLUMN_STREAK = "STREAK";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_GAMETYPE = "GAMETYPE";

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_PASSWORD = "PASSWORD";

    public Database(@Nullable Context context)
    {
        super(context, "score.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTableStatementScore = "CREATE TABLE " + SCORE_TABLE + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_POINTS + " INT, " +
                COLUMN_ACCURACY + " FLOAT, " +
                COLUMN_CORRECT + " INT, " +
                COLUMN_INCORRECT + " INT, " +
                COLUMN_STREAK + " INT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_GAMETYPE + " TEXT)";

        String createTableStatementUser = "CREATE TABLE " + USER_TABLE + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT)";

        db.execSQL(createTableStatementScore);
        db.execSQL(createTableStatementUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    //All scores with no specific GameType.
    public List<Score> getAllScores()
    {
        List<Score> returnList = new ArrayList<Score>();
        String queryString = "SELECT * FROM " + SCORE_TABLE +

                " ORDER BY " + COLUMN_ACCURACY +
                " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst())
        {
            do
            {
                try
                {
                    String username = cursor.getString(1);
                    int points = cursor.getInt(2);
                    int correct = cursor.getInt(4);
                    int incorrect = cursor.getInt(5);
                    int streak = cursor.getInt(6);
                    String date = cursor.getString(7);
                    String time = cursor.getString(8);
                    Score.GameType gameType = Score.GameType.valueOf(cursor.getString(9));

                    Score score = new Score(username, points, correct, incorrect, streak, date,
                            time, gameType);
                    returnList.add(score);
                } catch(Exception e)
                {
                    System.out.println("Problems with the database");
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    //All scores with specified GameType
    public List<Score> getAllScores(Score.GameType gameType)
    {
        List<Score> returnList = new ArrayList<Score>();
        int rank = 1;
        for (Score score: getAllScores())
        {
            if (score.getGameType() == gameType)
            {
                score.setRank(rank);
                rank++;
                returnList.add(score);
            }
        }
        return returnList;
    }

    public boolean addScore(Score score)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, score.getUsername());
        cv.put(COLUMN_POINTS, score.getPoints());
        cv.put(COLUMN_ACCURACY, score.getAccuracy());
        cv.put(COLUMN_CORRECT, score.getCorrect());
        cv.put(COLUMN_INCORRECT, score.getIncorrect());
        cv.put(COLUMN_STREAK, score.getBestStreak());
        cv.put(COLUMN_DATE, score.getDate());
        cv.put(COLUMN_TIME, score.getTime());
        cv.put(COLUMN_GAMETYPE, score.getGameType().toString());

        long insert = db.insert(SCORE_TABLE, null, cv);
        return insert != -1;
    }

    public String toString()
    {
        StringBuilder returnString = new StringBuilder();
        for (Score score: this.getAllScores())
        {
            returnString.append(score.toString());
        }
        return returnString.toString();
    }

    /**
     *
     * @param score
     * @return the rank of the score in its corresponding GameType.
     */
    public int getRank(Score score)
    {
        List<Score> allScore = getAllScores(score.getGameType());
        int rank = 0;
        int i = 0;
        while (i < allScore.size())
        {
            if (allScore.get(i).getTime().equals(score.getTime()) && allScore.get(i).getDate()
                    .equals(score.getDate()))
            {
                rank = i + 1;
                break;
            }
            i++;
        }
      return rank;
    }



    public boolean addUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_PASSWORD, user.getPassword());

        long insert = db.insert(USER_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<User> getAllUsers()
    {
        ArrayList<User> returnList = new ArrayList<User>();
        String queryString = "SELECT * FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst())
        {
            do
            {
                try
                {
                    String username = cursor.getString(0);
                    String password = cursor.getString(1);
                    User user = new User(username, password);
                    returnList.add(user);
                } catch(Exception e)
                {
                    System.out.println("Problems with the database");
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

}

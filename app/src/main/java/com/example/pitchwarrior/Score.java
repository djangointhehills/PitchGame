package com.example.pitchwarrior;
import java.util.ArrayList;

public class Score {
    private String username;
    int points;
    private int correct;
    private int incorrect;
    int bestStreak;
    int currentStreak;
    private String date;
    private String time;
    private GameType gameType;
    int rank;
    private ArrayList<ArrayList<Interval>> intervalResults;
    Note previousNote;
    Note currentNote;


    public enum GameType
    {
        THIRTYSECONDS(30000), ONEMIN(60000), TWOMIN(120000);

        int gameLength;

        GameType(int i)
        {
            this.gameLength = i;
        }
    }

    public int getRank()
    {
        return rank;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public Score()
    {
        this.username = MainActivity.user.getUsername();
        this.points = 0;
        this.correct = 0;
        this.incorrect = 0;
        this.bestStreak = 0;
        this.currentStreak = 0;
        this.date = "Default";
        this.time = "Default";
        this.gameType = GameType.THIRTYSECONDS;
        currentStreak = 0;
        currentNote = new Note("C", 1);
        intervalResults = new ArrayList<>();
        Interval interval = new Interval(currentNote);
    }

    public void addNewGeneratedInterval(int noteNumber)
    {
        Interval interval = new Interval(previousNote, Keyboard.getNote(noteNumber));
        intervalResults.get(0).add(interval);
    }

    public void nextNote(int noteNumber)
    {
        previousNote = currentNote;
        currentNote = Keyboard.getNote(noteNumber);
    }

    public Score newScoreWithCopiedGameType(Score score)
    {
        Score newScore = new Score();
        newScore.setGameType(score.getGameType());
        return newScore;
    }

    public GameType getGameType()
    {
        return gameType;
    }

    public void setGameType(GameType gameType)
    {
        this.gameType = gameType;
    }

    public int getCurrentStreak()
    {
        return currentStreak;
    }

    public void addOneToCurrentStreak()
    {
        currentStreak++;
    }

    public void resetCurrentStreak()
    {
        this.currentStreak = 0;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public Score(String username, int points, int correct, int incorrect, int bestStreak,
                 String date, String time, GameType gameType)
    {
        this.username = username;
        this.points = points;
        this.correct = correct;
        this.incorrect = incorrect;
        this.bestStreak = bestStreak;
        this.date = date;
        this.time = time;
        this.gameType = gameType;
    }


    public Score(String username, int correct, int incorrect, int bestStreak, GameType gameType,
                 String date, String time)
    {
        this.username = username;
        this.correct = correct;
        this.incorrect = incorrect;
        this.bestStreak = bestStreak;
        this.gameType = gameType;
        this.date = date;
        this.time = time;
        this.currentStreak = 0;
    }

    public int getBestStreak()
    {
        return bestStreak;
    }

    public void setBestStreak(int bestStreak)
    {
        this.bestStreak = bestStreak;
    }

    public int getCorrect() {
        return correct;
    }

    public void addCorrect() {
        points = points + 5;
        correct++;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void addIncorrect() {
        points = points - 3;
        incorrect++;
    }

    public int getTotalAttempts()
    {
        return this.correct + this.incorrect;
    }

    public double getAccuracy()
    {
        if (correct == 0 || this.getTotalAttempts() == 0)
        {
            return 0;
        }
        double percentage = (double) correct / (double) this.getTotalAttempts();
        return Math.floor(percentage * 1000) / 10;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString()
    {
        return "" + this.username +
                "   " + this.getAccuracy() +
                "   " + this.correct +
                "   " + this.incorrect +
                "   " + this.bestStreak +
                "   " + this.date +
                "   " + this.time;
    }
}

package com.example.pitchwarrior;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//TODO Create an information page that gives the rules/instruction on how to play the game.
//TODO Improve the look.
//TODO voice recognition of intervals?
//TODO Vibration for haptic feedback.
//TODO Different sound for computer generated notes.
//TODO two octaves.
//TODO Change the start note of the two octaves, so a variation of registers are used.
//TODO Get the chance of an interval being played to increase as the user gets it wrong more often.
//TODO Implement space invader style version of the game.
//TODO Perhaps a two player variation of the game could exist?
//TODO have one octave for portrait and two octaves for landscape.
//TODO have the starting note change.
//TODO need notes to light up sometimes. Find out how to do this.


public class Game extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (parent.getSelectedItemPosition())
        {
            case 0:
                gameScore.setGameType(Score.GameType.THIRTYSECONDS);
                break;
            case 1:
                gameScore.setGameType(Score.GameType.ONEMIN);
                break;
            case 2:
                gameScore.setGameType(Score.GameType.TWOMIN);
        }
        resetCountDownText();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        gameScore.setGameType(Score.GameType.THIRTYSECONDS);
    }



    //To play note sounds.
    private SoundPool soundPool;

    private int cNat, cSha, dNat, dSha, eNat, fNat, fSha, gNat, gSha, aNat, aSha, bNat;

    // noteList is going to be the list which connects the random int to the notes to be played.
    private int[] noteList;



    private boolean correctNote;

    //Has a value from 1-12
    private int notePushed;

    //Feedback on answers and what to do.
    private TextView feedback;
    private String[] feedBackStringArray = new String[]{"Get ready to play!" , "Game about to start. Here's the note C.", "Correct", "Incorrect"};

    //Display score variables.
    TextView points = null;
    TextView accuracy = null;
    TextView bestStreak = null;
    TextView currentStreak = null;

    //Timer variables
    private boolean gameInProgress = false;
    private boolean gameCountDownInProgress = false;
    private long timeLeftInMillis;
    TextView textViewTimer;

    public static Score gameScore;


    Random randomNumGenerator;
    long noteDelay;
    long timeBetweenNotes = 3500;
    // rand_int1 is the random integer generated to choose which note will be played. Has value from
    //1-12
    private int randNote;
    //This is to stop the same note playing twice in a row.
    private int newRandomNote;

    Timer timer;
    TimerTask playNoteTimerTask;


    //30 second game.
    Button recordsButton;

    Intent recordsIntent;

    Intent intentResults;

    //Timer
    CountDownTimer gameCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initialise score.
        gameScore = new Score();

        //Initialise GameType spinner
        Spinner gameTypeSpinner = (Spinner) findViewById(R.id.gameTypeSpinner);
        ArrayAdapter<String> gameTypeAdapter = new ArrayAdapter<String>(Game.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gameTypes));
        gameTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameTypeSpinner.setAdapter(gameTypeAdapter);
        gameTypeSpinner.setOnItemSelectedListener(this);

        //Initialise timer.
        textViewTimer = findViewById(R.id.Timer);
        resetCountDownText();

        //Initialise the feedback.
        feedback = findViewById(R.id.gameFeedbackTextView);

        //Initialise scoring views.
        points = (TextView) findViewById(R.id.points);
        accuracy = (TextView) findViewById(R.id.accuracy);
        bestStreak = (TextView) findViewById(R.id.bestStreak);
        currentStreak = (TextView) findViewById(R.id.currentStreak);

        setScore();

        //Initialise Records button.
        recordsButton = (Button) findViewById(R.id.records);
        recordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordsIntent = new Intent(Game.this, ViewListContents.class);
                startActivity(recordsIntent);
            }
        });

        //Initialise results pop up.
        intentResults = new Intent(Game.this, Results.class);

        //Initialise random note generator
        randomNumGenerator = new Random();


        //Initialise note sounds.
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build();

        cNat = soundPool.load(this, R.raw.cnatfile, 1);
        cSha = soundPool.load(this, R.raw.cshafile, 1);
        dNat = soundPool.load(this, R.raw.dnatfile, 1);
        dSha = soundPool.load(this, R.raw.dshafile, 1);
        eNat = soundPool.load(this, R.raw.enatfile, 1);
        fNat = soundPool.load(this, R.raw.fnatfile, 1);
        fSha = soundPool.load(this, R.raw.fshafile, 1);
        gNat = soundPool.load(this, R.raw.gnatfile, 1);
        gSha = soundPool.load(this, R.raw.gshafile, 1);
        aNat = soundPool.load(this, R.raw.anatfile, 1);
        aSha = soundPool.load(this, R.raw.ashafile, 1);
        bNat = soundPool.load(this, R.raw.bnatfile, 1);

        noteList = new int[] {cNat, cSha, dNat, dSha, eNat, fNat,
                fSha, gNat, gSha, aNat, aSha, bNat};

    }

    private void playNote(int noteNumber)
    {
        soundPool.play(noteList[noteNumber - 1], 1, 1, 0, 0, 1);
    }

    private void setScore()
    {
        points.setText("Points = " + gameScore.getPoints());
        accuracy.setText("Accuracy = " + gameScore.getAccuracy());
        bestStreak.setText("Best streak = " + gameScore.getBestStreak());
        currentStreak.setText("Current streak = " + gameScore.getCurrentStreak());
    }

    private void resetCountDownText()
    {
        int minutes = (int) (gameScore.getGameType().gameLength / 1000) / 60;
        int seconds = (int) (gameScore.getGameType().gameLength / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        timeLeftFormatted = "Time Left = " + timeLeftFormatted;
        textViewTimer.setText(timeLeftFormatted);
    }


    private void upDateCountDownText()
    {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        timeLeftFormatted = "Time Left = " + timeLeftFormatted;
        textViewTimer.setText(timeLeftFormatted);
    }


    private void upDateCountDownIntoGameText()
    {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        timeLeftFormatted = "Start in " + timeLeftFormatted;
        textViewTimer.setText(timeLeftFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

    private void addScoreToDB(Score score)
    {
        MainActivity.database.addScore(score);
    }

    public void OnClickStartGame(View view)
    {
        if (!gameInProgress && !gameCountDownInProgress)
        {
            countDownIntoGame();
        }
    }

    public void countDownIntoGame()
    {
        gameCountDownInProgress = true;
        feedback.setText(feedBackStringArray[1]);
        gameScore = gameScore.newScoreWithCopiedGameType(gameScore);
        setScore();
        //Play the note C for player to hear first.
        playNote(1);
        timeLeftInMillis = 3000;
        gameCountDownTimer = new CountDownTimer(timeLeftInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                upDateCountDownIntoGameText();
            }

            @Override
            public void onFinish() {
                resetCountDownText();
                startTimer();
            }
        }.start();
    }

    private void startTimer()
    {
        gameScore = gameScore.newScoreWithCopiedGameType(gameScore);
        setScore();
        gameInProgress = true;
        correctNote = true;
        noteDelay = 0;
        startPlayingNotes();
        timeLeftInMillis = gameScore.getGameType().gameLength;
        gameCountDownTimer = new CountDownTimer(timeLeftInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                upDateCountDownText();
            }

            @Override
            public void onFinish() {
                timer.cancel();
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                gameScore.setDate(currentDate);
                gameScore.setTime(currentTime);
                addScoreToDB(gameScore);
                gameInProgress = false;
                gameCountDownInProgress = false;
                startActivity(intentResults);
                feedback.setText(feedBackStringArray[0]);
            }
        }.start();
    }

    private void startPlayingNotes()
    {
        if (correctNote)
        {
            randomNumGenerator.setSeed(System.currentTimeMillis());
            newRandomNote = randomNumGenerator.nextInt(12) + 1;
            while (newRandomNote == randNote)
            {
                newRandomNote = randomNumGenerator.nextInt(12) + 1;
            }
            randNote = newRandomNote;
            correctNote = false;
        }
        //Initialise timer for auto-generate notes
        timer = new Timer();
        playNoteTimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                soundPool.play(noteList[randNote], 1, 1, 0, 0, 1);
            }
        };
        if(gameInProgress)
        {
            timer.schedule(playNoteTimerTask, noteDelay, timeBetweenNotes);
        }

    }

    /**
     * This plays the note and assigns it to button pushed.
     * @param
     */
    public void onClickUserPlaySound(View v) {
        switch (v.getId()) {
            case R.id.cNatButton:
                notePushed = 1;
                break;
            case R.id.cShaButton:
                notePushed = 2;
                break;
            case R.id.dNatButton:
                notePushed = 3;
                break;
            case R.id.dShaButton:
                notePushed = 4;
                break;
            case R.id.eNatButton:
                notePushed = 5;
                break;
            case R.id.fNatButton:
                notePushed = 6;
                break;
            case R.id.fShaButton:
                notePushed = 7;
                break;
            case R.id.gNatButton:
                notePushed = 8;
                break;
            case R.id.gShaButton:
                notePushed = 9;
                break;
            case R.id.aNatButton:
                notePushed = 10;
                break;
            case R.id.aShaButton:
                notePushed = 11;
                break;
            case R.id.bNatButton:
                notePushed = 12;
                break;
        }
        if (gameInProgress)
        {
            timer.cancel();
            noteDelay = 1000;
            playNote(notePushed);
            isCorrectNote(notePushed);
            this.startPlayingNotes();
        } else
        {
            playNote(notePushed);
        }
    }

    private void isCorrectNote(int note)
    {
        if (gameInProgress)
        {
            if (note - 1 == randNote)
            {
                gameScore.addCorrect();
                gameScore.addOneToCurrentStreak();
                feedback.setText(feedBackStringArray[2]);
                if (gameScore.getCurrentStreak() > gameScore.getBestStreak())
                {
                    gameScore.setBestStreak(gameScore.getCurrentStreak());
                }
                correctNote = true;
            } else
            {
                gameScore.addIncorrect();
                gameScore.resetCurrentStreak();
                feedback.setText(feedBackStringArray[3]);
            }
            this.setScore();
        }
    }

    public void OnClickEndGame(View view)
    {
        if (gameInProgress || gameCountDownInProgress)
        {
            timer.cancel();
            gameCountDownTimer.cancel();
            gameInProgress = false;
            gameCountDownInProgress = false;
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            gameScore.setDate(currentDate);
            gameScore.setTime(currentTime);
            addScoreToDB(gameScore);
            gameInProgress = false;
            gameCountDownInProgress = false;
            startActivity(intentResults);
            feedback.setText(feedBackStringArray[0]);
            setScore();
            resetCountDownText();
        }
    }
}
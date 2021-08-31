package com.example.pitchwarrior;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

//TODO Maybe employ a google login.

public class MainActivity extends AppCompatActivity
{

    private TextInputLayout usernameTIL;
    private TextInputLayout passwordTIL;

    private TextInputLayout createUsernameTIL;
    private TextInputLayout createPasswordTIL;
    private  TextInputLayout confirmPasswordTIL;

    private boolean validUsername;

    //Database
    public static Database database;

    static User user;

    //Link to game.
    Intent playGameIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Initialise SQL database.
        database = new Database(MainActivity.this);

        setContentView(R.layout.activity_main);

        //Initialise login.
        usernameTIL = findViewById(R.id.UsernameTextInputLayout);
        passwordTIL = findViewById(R.id.passwordTextInputLayout);


        //Initialise create new user.
        createUsernameTIL = findViewById(R.id.newUsernameTextInputLayout);
        createPasswordTIL = findViewById(R.id.newPasswordTextInputLayout);
        confirmPasswordTIL = findViewById(R.id.newConfirmPasswordTextInputLayout);

        //Initialise intent
        playGameIntent = new Intent(MainActivity.this, Game.class);
    }

    private boolean validateUsernamePassword()
    {
        String usernameInput = usernameTIL.getEditText().getText().toString().trim();
        String passwordInput = passwordTIL.getEditText().getText().toString().trim();
        boolean validUsername = false;
        boolean correctPassword = false;

        if (database.getAllUsers().size() > 0)
        {
            for (User user: database.getAllUsers())
            {
                if (user.getUsername().equals(usernameInput))
                {
                    validUsername = true;
                    usernameTIL.setError(null);
                    if (user.getPassword().equals(passwordInput))
                    {
                        correctPassword = true;
                        this.user = user;
                        break;
                    }
                    break;
                }
            }
        }

        if (!validUsername)
        {
            usernameTIL.setError("Invalid username. Please try again.");
        } else if (!correctPassword)
        {
            passwordTIL.setError("Incorrect password. Please try again.");
        } else
        {
            usernameTIL.setError(null);
            usernameTIL.setErrorEnabled(false);
            passwordTIL.setError(null);
            passwordTIL.setErrorEnabled(false);
        }
        return correctPassword;
    }


    public void confirmInput(View v)
    {
        if (validateUsernamePassword())
        {
            String input = "Welcome " + user.getUsername();
            input += ".\n";
            input += "Have fun playing!";
            input += "\n";
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
            startActivity(playGameIntent);
        }
    }

    public boolean validNewUser()
    {
        String username = createUsernameTIL.getEditText().getText().toString();
        boolean validUsername = true;
        for (User user: database.getAllUsers())
        {
            if(user.getUsername().equals(username))
            {
                validUsername = false;
                break;
            }
        }
        if (validUsername)
        {
            createUsernameTIL.setError(null);
            createUsernameTIL.setErrorEnabled(false);
        } else
        {
            String input = "Username: " + createUsernameTIL.getEditText().getText().toString();
            input += ", already exists. Please choose another username.";
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        }
        return validUsername;
    }

    public boolean validNewPassword()
    {
        String passwordOne = createPasswordTIL.getEditText().getText().toString();
        String passwordTwo = confirmPasswordTIL.getEditText().getText().toString();
        boolean validPassword = false;
        if (!passwordOne.equals(passwordTwo))
        {
            String input = "Passwords do not match. Please try again.";
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        } else if (passwordOne.length() > 15)
        {
            String input = "Password needs to be less than 15 characters. Please try again.";
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        } else
        {
            createPasswordTIL.setError(null);
            createPasswordTIL.setErrorEnabled(false);
            confirmPasswordTIL.setError(null);
            confirmPasswordTIL.setErrorEnabled(false);
            validPassword = true;
        }
        return validPassword;
    }

    public void confirmCreateUser(View v)
    {
        if (validNewUser() && validNewPassword())
        {
            String username = createUsernameTIL.getEditText().getText().toString();
            String password = createPasswordTIL.getEditText().getText().toString();
            User user = new User(username, password);
            database.addUser(user);
            this.user = user;
            String input = "Welcome " + username;
            input += ".\n";
            input += "Have fun playing!";
            input += "\n";
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
            startActivity(playGameIntent);
        }
    }
}

